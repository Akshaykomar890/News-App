package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.converter.NewsConverters
import com.example.newsapp.data.local.database.BookmarkDatabase
import com.example.newsapp.data.local.database.NewsDatabase
import com.example.newsapp.data.remote.web.NewsApi
import com.example.newsapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(30L,TimeUnit.SECONDS) //30L Long
            .writeTimeout(30L,TimeUnit.SECONDS) //30L Long
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(app:Application):NewsDatabase{
        return Room.databaseBuilder(
            context = app,
            NewsDatabase::class.java,"News-db",
        ).addTypeConverter(NewsConverters())
            .build()
    }


    @Singleton
    @Provides
    fun provideBookmarkDatabase(app:Application):BookmarkDatabase{
        return Room.databaseBuilder(
            context = app,
            BookmarkDatabase::class.java,"Bookmark-db",
        ).addTypeConverter(NewsConverters())
            .build()
    }

    


}