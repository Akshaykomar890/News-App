package com.example.newsapp.destination

import com.example.newsapp.domain.model.NewsList
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Bookmark

@Serializable
object Bottom

@Serializable
data class Details(
    val id:Int,
)