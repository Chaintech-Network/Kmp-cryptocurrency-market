@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose)
    kotlin("plugin.serialization") version "1.9.10"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                dependencies {
                    /*compose*/
                    implementation(compose.runtime)
                    implementation(compose.foundation)
                    implementation(compose.material)
                    implementation(compose.material3)
                    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                    implementation(compose.components.resources)

                    /*ktor - networking*/
                    implementation(libs.ktor.core)
                    implementation(libs.ktor.client.content.negotiation)
                    implementation(libs.ktor.client.logging)
                    api(libs.ktor.serialization.kotlinx)
                    implementation(libs.ktor.web.sockets)

                    /*serialization*/
                    implementation(libs.kotlinx.serialization.json)

                    /*koin - dependency injection*/
                    implementation(libs.koin.core)
                    implementation(libs.koin.compose)

                    /*coroutines*/
                    implementation(libs.kotlinx.coroutines.core)

                    /*image-loader*/
                    api(libs.image.loader)

                    implementation(project(":timber"))
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.android.accompanist.systemuicontroller)
                api(libs.android.activity.compose)
                api(libs.android.appcompat)
                api(libs.android.core.ktx)
                implementation(libs.android.ktor.client)
                implementation(libs.android.ktor.client.okhttp)
                implementation(libs.android.koin.core)
                implementation(libs.android.kotlinx.coroutines)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ios.ktor.client.darwin)
            }
        }
    }
}

android {
    namespace = "com.example.binancepriceticker"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].apply {
        res.srcDirs("src/commonMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }

    defaultConfig {
        minSdk = 24
    }
}