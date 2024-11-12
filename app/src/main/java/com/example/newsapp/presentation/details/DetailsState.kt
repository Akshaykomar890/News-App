package com.example.newsapp.presentation.details

import com.example.newsapp.domain.model.NewsList

data class DetailsState(
    val newsId:NewsList? = null ,
    val errorMessage:String? = null
)