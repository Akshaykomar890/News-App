package com.example.newsapp.data.mapper

import com.example.newsapp.data.local.model.NewsEntity
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.remote.response.NewsDto
import com.example.newsapp.data.remote.response.NewsListDto
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.model.NewsList




fun NewsEntityList.toNewsList():NewsList{
    return NewsList(
        author = author,
        category = category,
        image = image,
        language = language,
        publish_date = publish_date,
        sentiment = sentiment,
        source_country = source_country,
        summary = summary,
        text = text,
        title = title,
        url = url,
        video = video,
        id = id,
        authors = authors
    )
}


fun NewsDto.toNewsEntity():NewsEntity{
    return NewsEntity(
        available = available?:0,
        news = news?.mapNotNull { it?.toNewsList()}?:emptyList(),
        number = number?:0,
        offset = offset?:0
    )
}


fun NewsListDto.toNewsList():NewsEntityList{
    return NewsEntityList(
        category = category?:"",
        id = id?:0,
        image = image?:"",
        language = language?:"",
        publish_date = publish_date?:"",
        source_country = source_country?:"",
        summary = summary?:"",
        text = text?:"",
        title = title?:"",
        url = url?:"",
        video = video?:"",
        authors = authors?.mapNotNull { it.toString() }?: emptyList<String>(),
        author = author?:"",
        sentiment = sentiment?:0.0

    )
}


