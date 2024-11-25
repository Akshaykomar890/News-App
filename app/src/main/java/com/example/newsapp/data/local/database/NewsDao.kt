package com.example.newsapp.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.data.local.model.NewsEntity
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.local.model.NewsEntityListBookmark
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {


    @Query("Select * from NewsEntityList where id = :id")
    suspend fun getNewsById(
        id:Int
    ):NewsEntityList


    @Query("Select * from NewsEntityList")
    suspend fun getNewsList():List<NewsEntityList>

    @Query("DELETE FROM NewsEntityList")
    suspend fun clearNews()


    @Upsert
    suspend fun insertNews(
        news: List<NewsEntityList>
    )


}