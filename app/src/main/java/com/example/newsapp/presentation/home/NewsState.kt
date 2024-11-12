package com.example.newsapp.presentation.home

import com.example.newsapp.domain.model.NewsList

data class NewsState(
    val newsList:List<NewsList> = emptyList(),
    val errorMessage:String? = null
)
