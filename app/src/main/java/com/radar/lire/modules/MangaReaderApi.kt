package com.radar.lire.modules

import kotlinx.serialization.Serializable

@Serializable
data class TrendingMangaReaderResponse(
    val count: Int,
    val next: String?,
    val prev: String?,
    val data: List<TrendingMangaReaderManga>
)

@Serializable
data class TrendingMangaReaderManga(
    val title: String,
    val slug: String,
    val cover: String,
    val rating: Double,
    val langs: List<String>,
    val chapters: Double,
    val volumes: Float
)


@Serializable
data class FeaturedMangaReaderResponse(
    val count: Int,
    val next: String?,
    val prev: String?,
    val data: List<FeaturedMangaReaderManga>
)

@Serializable
data class FeaturedMangaReaderManga(
    val title: String,
    val slug: String,
    val genres: List<String>,
    val cover: String,
    val synopsis: String,
    val chapter: Float
)

