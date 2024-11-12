package com.example.newsapp.data.remote.response

data class NewsDto(
    val available: Int?,
    val news: List<NewsListDto?>?,
    val number: Int?,
    val offset: Int?
)