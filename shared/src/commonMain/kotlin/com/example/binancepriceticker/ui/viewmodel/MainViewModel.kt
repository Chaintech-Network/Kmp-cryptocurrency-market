package com.example.binancepriceticker.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.binancepriceticker.core.ViewModel
import com.example.binancepriceticker.model.CoinData
import com.example.binancepriceticker.model.SpotTicker
import com.example.binancepriceticker.repository.MainRepository
import com.example.binancepriceticker.ui.util.SortParams
import com.example.binancepriceticker.ui.util.swapList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainViewModel constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _tickerResponse: MutableState<TickerState> = mutableStateOf(TickerState())
    val tickerResponse: State<TickerState> = _tickerResponse

    private val _symbolsResponse: MutableState<SymbolsState> = mutableStateOf(SymbolsState())
    val symbolsResponse: State<SymbolsState> = _symbolsResponse

    private var symbolsMap: Map<String, Pair<String, String>> = hashMapOf()
    val quoteCoinList = mutableStateListOf<String>()

    private val allTickers = arrayListOf<SpotTicker>()
    private val tickerList = arrayListOf<SpotTicker>()
    val tickerSortedList = mutableStateListOf<SpotTicker>()
    var isScrolling = false
    var selectedTabIndex = mutableStateOf(0)
    var showNoData = mutableStateOf(false)

    val currentSortKey = mutableStateOf(SortParams.Vol)
    val isSortDesc = mutableStateOf(false)
    val basePrice = mutableStateOf(0.0)
    var searchText = MutableStateFlow("")
    val mutex = Mutex()

    private fun getTickers() {
        viewModelScope.launch {
            repository.getTickers()
                .onStart {
                    _tickerResponse.value = TickerState(
                        isLoading = true
                    )
                }.catch {
                    _tickerResponse.value = TickerState(
                        isLoading = false,
                        error = it.message ?: "Something went wrong!"
                    )
                }.collect {
                    val list = it.map {
                        if (symbolsMap.containsKey(it.symbol)) {
                            val data = symbolsMap[it.symbol]
                            it.base = data?.first.toString()
                            it.quote = data?.second.toString()
                        }
                        it
                    }.filter { it.base.isNotEmpty() && it.quote.isNotEmpty() }
                    _tickerResponse.value = TickerState(
                        isLoading = false,
                        data = list
                    )
                    allTickers.clear()
                    allTickers.addAll(list)
                    updateTicker()
                    initTicker()
                }
        }
    }

    fun getSymbols() {
        viewModelScope.launch {
            repository.getSymbols()
                .onStart {
                    _symbolsResponse.value = SymbolsState(
                        isLoading = true
                    )
                }.catch {
                    _symbolsResponse.value = SymbolsState(
                        error = it.message ?: "Something went wrong!"
                    )
                }.collect {
                    it.data?.let { symbolList ->
                        _symbolsResponse.value = SymbolsState(
                            data = symbolList
                        )
                        symbolsMap = symbolList.associate { data ->
                            Pair(
                                data?.symbol ?: "",
                                Pair(data?.base ?: "", data?.quote ?: "")
                            )
                        }
                        quoteCoinList.clear()
                        quoteCoinList.addAll(symbolList.map { it?.quote ?: "" }.distinct())
                    }
                    getTickers()
                }
        }
    }

    private fun initTicker() {
        viewModelScope.launch {
            repository.initTicker().collectLatest { updatedList ->
                try {
                    mutex.withLock {
                        val iterator = updatedList.iterator()
                        while (iterator.hasNext()) {
                            val item = iterator.next()
                            val index = tickerSortedList.indexOfFirst { it.symbol == item.symbol }
                            if (index != -1 && !isScrolling) {
                                tickerSortedList[index] =
                                    tickerSortedList[index].copy(
                                        lastPrice = item.lastPrice,
                                        priceChange = item.priceChange,
                                        priceChangePercent = item.priceChangePercent,
                                        quoteVolume = item.quoteVolume,
                                        volume = item.volume
                                    )
                                updateBasePrice()
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun updateTicker() {
        if (quoteCoinList.isNotEmpty()) {
            val quote = quoteCoinList[selectedTabIndex.value]
            val list = allTickers.filter { it.quote == quote }
            tickerSortedList.swapList(list)
            showNoData.value = tickerSortedList.isEmpty()
            tickerList.clear()
            tickerList.addAll(list)
        }
    }

    fun onClickTab(index: Int) {
        if (selectedTabIndex.value != index) {
            currentSortKey.value = SortParams.Vol
            isSortDesc.value = false
            selectedTabIndex.value = index
            updateTicker()
            updateBasePrice()
        }
    }

    fun updateSortKey(key: SortParams) {
        viewModelScope.launch {
            isSortDesc.value = if (currentSortKey.value != key) true else !isSortDesc.value!!
            currentSortKey.value = key
            applySort()
        }
    }


    private suspend fun applySort() {
        mutex.withLock {
            when (currentSortKey.value) {
                SortParams.Default -> tickerSortedList
                SortParams.Pair -> if (!isSortDesc.value) tickerSortedList.sortByDescending { it.symbol } else tickerSortedList.sortBy { it.symbol }
                SortParams.Vol -> if (!isSortDesc.value) tickerSortedList.sortByDescending { it.quoteVolume.toDouble() } else tickerSortedList.sortBy { it.quoteVolume.toDouble() }
                SortParams.Price -> if (!isSortDesc.value) tickerSortedList.sortByDescending { it.lastPrice.toDouble() } else tickerSortedList.sortBy { it.lastPrice.toDouble() }
                SortParams.Change -> if (!isSortDesc.value) tickerSortedList.sortByDescending { it.priceChangePercent.toDouble() } else tickerSortedList.sortBy { it.priceChangePercent.toDouble() }
            }
        }
    }

    private fun updateBasePrice() {
        if (quoteCoinList.isNotEmpty()) {
            val quote = quoteCoinList[selectedTabIndex.value]
            if (quote == "USDT") {
                basePrice.value = 1.0
            } else {
                basePrice.value =
                    allTickers.find { it.symbol == quote.uppercase() + "USDT" }?.lastPrice?.toDouble()
                        ?: 0.0
            }
        }
    }

    fun searchDebounce() {
        searchText
            .debounce(700)
            .onEach {
                searchTicker(it)
            }
            .launchIn(viewModelScope)
    }

    private fun searchTicker(text: String) {
        val list = tickerList.filter { it.symbol.lowercase().contains(text.lowercase()) }
        tickerSortedList.swapList(list)
        showNoData.value = tickerSortedList.isEmpty()
    }

}

data class TickerState(
    val data: List<SpotTicker> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = true
)

data class SymbolsState(
    val data: List<CoinData.Data?> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
