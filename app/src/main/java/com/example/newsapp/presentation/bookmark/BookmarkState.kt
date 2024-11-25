package com.example.newsapp.presentation.bookmark

import com.example.newsapp.domain.model.NewsBookmarkList
import com.example.newsapp.domain.model.NewsList

data class BookmarkState(
    val newsBookmark:List<NewsBookmarkList> = emptyList(),
    val bookmarkData:NewsBookmarkList? = null
)