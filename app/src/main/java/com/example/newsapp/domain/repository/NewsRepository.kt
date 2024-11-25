package com.example.newsapp.domain.repository

import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.model.NewsBookmarkList
import com.example.newsapp.domain.model.NewsList
import com.example.newsapp.utils.SetResults
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsList(
        text:String?,
    ):Flow<SetResults<List<NewsList>>>

    suspend fun getNewsId(
        id:Int,
    ):Flow<SetResults<NewsList>>


    suspend fun onRefresh()

    suspend fun addBookmark(news:NewsBookmarkList)

    suspend fun getBookmarkList():Flow<SetResults<List<NewsBookmarkList>>>

    suspend fun getNewBookmarkById(id:Int):Flow<SetResults<NewsBookmarkList>>











}