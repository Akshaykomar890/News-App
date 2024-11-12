package com.example.newsapp.data.remote.web

import com.example.newsapp.data.remote.response.NewsDto
import com.example.newsapp.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/search-news")
    suspend fun getNewsApi(
        @Query("text") text:String?,
        @Query("language") language:String = "en",
        @Query("source-country") country:String="in",
        @Query("api-key") apiKey:String = API_KEY
    ): NewsDto

}