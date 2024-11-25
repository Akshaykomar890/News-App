package com.example.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.local.converter.NewsConverters
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.local.model.NewsEntityListBookmark


@Database(entities = [NewsEntityListBookmark::class], version = 1, exportSchema = false)
@TypeConverters(NewsConverters::class)
abstract class BookmarkDatabase :RoomDatabase(){
    abstract fun bookmarkDao():BookmarkDao
}