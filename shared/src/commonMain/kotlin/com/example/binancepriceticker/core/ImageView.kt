package com.example.binancepriceticker.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.intercept.Interceptor
import com.seiko.imageloader.model.ImageEvent
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.model.NullRequestData
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FromLocalDrawable(
    painterResource: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    colorFilter: ColorFilter? = null,
    onClick: (() -> Unit?)? = null
) {
    androidx.compose.foundation.Image(
        painter = painterResource(painterResource),
        contentDescription = "",
        contentScale = contentScale,
        colorFilter = colorFilter,
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.clickable {
                    onClick()
                }
            } else {
                Modifier
            }
        )
    )
}

@Composable
fun FromRemote(
    painterResource: String,
    modifier: Modifier = Modifier,
    placeholder: String = "ic_placeholder.webp",
    showPlaceHolder: Boolean = true,
    contentScale: ContentScale = ContentScale.FillWidth,
) {
    Image(
        data = painterResource,
        modifier = modifier,
        showPlaceHolder = showPlaceHolder,
        placeholder = placeholder,
        contentScale = contentScale
    )
}

@Composable
fun Image(
    modifier: Modifier,
    data: Any,
    showPlaceHolder: Boolean = true,
    placeholder: String,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(modifier, Alignment.Center) {
        val request = remember(data, 0, true) {
            ImageRequest {
                data(data)
                addInterceptor(NullDataInterceptor)
                options {
                    playAnimate = true
                }
            }
        }
        val action by rememberImageAction(request)
        val painter = rememberImageActionPainter(action)
        androidx.compose.foundation.Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize(),
        )
        when (action) {
            is ImageEvent.StartWithDisk,
            is ImageEvent.StartWithFetch,
            -> {
                if (showPlaceHolder) CommonPlaceHolder(placeholder, contentScale)
            }

            is ImageResult.Source -> {

            }

            is ImageResult.Error -> {
                if (showPlaceHolder) CommonPlaceHolder(placeholder, contentScale)
            }

            else -> Unit
        }
    }
}

@Composable
fun CommonPlaceHolder(placeholder: String = "ic_placeholder.webp", contentScale: ContentScale) {
    FromLocalDrawable(
        painterResource = placeholder,
        contentScale = contentScale,
        modifier = Modifier.fillMaxWidth()
    )
}

object NullDataInterceptor : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        if (data === NullRequestData || data is String && data.isEmpty()) {
            return ImageResult.Painter(
                painter = EmptyPainter,
            )
        }
        return chain.proceed(chain.request)
    }

    private object EmptyPainter : Painter() {
        override val intrinsicSize: Size get() = Size.Unspecified
        override fun DrawScope.onDraw() {}
    }
}