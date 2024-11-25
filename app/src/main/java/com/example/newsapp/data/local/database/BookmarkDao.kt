package com.example.newsapp.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.local.model.NewsEntityListBookmark


@Dao
interface BookmarkDao {


    @Upsert
    suspend fun insertBookmark(news: NewsEntityListBookmark)

    @Query("SELECT * FROM NewsEntityListBookmark")
    suspend fun getNewsListBookmark():List<NewsEntityListBookmark>


    @Query("Select * from NewsEntityListBookmark where id = :id")
    suspend fun getNewsBookmarkById(
        id:Int
    ): NewsEntityListBookmark
}