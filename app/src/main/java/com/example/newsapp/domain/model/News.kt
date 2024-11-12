package com.example.newsapp.domain.model

data class News(
    val available: Int,
    val news: List<NewsList>,
    val number: Int,
    val offset: Int
)