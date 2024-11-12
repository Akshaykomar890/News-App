package com.example.newsapp.domain.model


data class NewsList(
    val author: String,
    val category: String,
    val id: Int,
    val image: String,
    val language: String,
    val publish_date: String,
    val sentiment: Double,
    val source_country: String,
    val summary: String,
    val text: String,
    val title: String,
    val url: String,
    val video: Any,
    val authors:List<String>
)