package com.example.gatos.network

import com.example.gatos.network.model.Cat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

object KtorHttpClient {

    private const val BASE_URL = "https://api.thecatapi.com/v1/images/search?limit=20&mime_types=png&size=med"

    val client = HttpClient(Android) {
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getCats(): Result<List<Cat>> {
        return requireGet(BASE_URL)
    }

    private suspend inline fun <reified T> requireGet(url: String): Result<T> {
        return try {
            Result.success(client.get(url).body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
 }