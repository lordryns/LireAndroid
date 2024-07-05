package com.radar.lire


import com.radar.lire.modules.FeaturedMangaReaderResponse
import com.radar.lire.modules.TrendingMangaReaderResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient(CIO){
    install(ContentNegotiation){
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }
}

const val baseUrl = "https://mangareader-api.vercel.app/api/v1"

suspend fun getTrendingManga(): TrendingMangaReaderResponse {
    val response: HttpResponse = client.get("$baseUrl/trending")

    return response.body()
}

suspend fun getFeaturedManga(): FeaturedMangaReaderResponse {
    val response: HttpResponse = client.get("$baseUrl/featured")

    return response.body()
}

