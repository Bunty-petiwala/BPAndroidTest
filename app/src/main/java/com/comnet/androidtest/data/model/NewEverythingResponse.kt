package com.comnet.androidtest.data.model

data class NewEverythingResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)