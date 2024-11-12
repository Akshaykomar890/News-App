package com.example.newsapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntityList(
    val author: String,
    val category: String,
    val image: String,
    val language: String,
    val publish_date: String,
    val sentiment: Double,
    val source_country: String,
    val summary: String,
    val text: String,
    val title: String,
    val url: String,
    val video:Any,
    val authors: List<String>,

    @PrimaryKey
    val id: Int,
)