package com.example.newsapp.data.local.model



data class NewsEntity(
    val available: Int,
    val news: List<NewsEntityList>,
    val number: Int,
    val offset: Int
)