package com.inspiredCoda.ktorclientyt.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.*
import io.ktor.utils.io.errors.*

class InternetInterceptor(
    val application: Application
) {

    private fun hasInternetConnection(): Boolean {
        val manager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    object Config {
        var application: Application? = null
    }

    companion object : HttpClientFeature<Config, InternetInterceptor> {
        override val key: AttributeKey<InternetInterceptor> = AttributeKey("InternetInterceptor")

        override fun install(feature: InternetInterceptor, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                if (!feature.hasInternetConnection()) {
                    throw NoInternetException("Check your internet connection and try again...")
                }
                proceedWith(subject)
            }
        }

        override fun prepare(block: Config.() -> Unit): InternetInterceptor {
            val config = Config.apply(block)
            return InternetInterceptor(
                config.application
                    ?: throw NullPointerException("Did you forget to pass in the application on the InternetInterceptor?")
            )
        }

    }

    class NoInternetException(message: String) : IOException(message)

}