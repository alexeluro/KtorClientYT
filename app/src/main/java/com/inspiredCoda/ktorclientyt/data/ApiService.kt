package com.inspiredCoda.ktorclientyt.data

import android.app.Application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*

interface ApiService {

    sealed class Routes(val url: String) {
        object UsersEndPoint : Routes("$BASE_URL/users")
    }

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        fun build(context: Application): HttpClient {
            return HttpClient(CIO) {
                install(InternetInterceptor) {
                    application = context
                }

                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.BODY
                }

                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

}