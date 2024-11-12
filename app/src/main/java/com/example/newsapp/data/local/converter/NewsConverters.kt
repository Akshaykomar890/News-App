package com.example.newsapp.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class NewsConverters {

    @TypeConverter
    fun fromVideo(video:Any?):String{
        return video.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun toVideo(videoGson:String?):Any?{
        return videoGson?.let {
            val type = object : TypeToken<Any>() {}.type
            Gson().fromJson(it,type)
        }
    }


    @TypeConverter
    fun fromList(authors: List<String>?): String {
        return Gson().toJson(authors)
    }

    @TypeConverter
    fun toList(authorsJson: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(authorsJson, type)
    }

}