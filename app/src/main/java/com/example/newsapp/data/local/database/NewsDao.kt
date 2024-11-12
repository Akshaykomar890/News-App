package com.example.newsapp.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.data.local.model.NewsEntity
import com.example.newsapp.data.local.model.NewsEntityList


@Dao
interface NewsDao {


    @Query("Select * from NewsEntityList where id = :id")
    suspend fun getNewsById(
        id:Int
    ):NewsEntityList


    @Query("Select * from NewsEntityList")
    suspend fun getNewsList():List<NewsEntityList>

    @Query("DELETE FROM NewsEntityList") // Replace 'news_table' with your actual table name
    suspend fun clearNews()


    @Upsert
    suspend fun insertNews(
        news: List<NewsEntityList>
    )




}