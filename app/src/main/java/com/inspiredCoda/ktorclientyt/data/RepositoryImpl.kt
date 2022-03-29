package com.inspiredCoda.ktorclientyt.data

import io.ktor.client.*
import io.ktor.client.request.*

class RepositoryImpl(
    private val httpClient: HttpClient
) : Repository {

    override suspend fun getUser(): NetworkEvents {
        return try {
            val response = httpClient.get<List<RemoteUser>>(ApiService.Routes.UsersEndPoint.url)
            NetworkEvents.Success(response.map { it.toUser() })
        } catch (ex: InternetInterceptor.NoInternetException) {
            NetworkEvents.Failure(ex.message ?: "Än error occurred")
        } catch (ex: Exception) {
            NetworkEvents.Failure(ex.message ?: "Än error occurred")
        }
    }
}