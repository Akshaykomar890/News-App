package com.example.newsapp.data.repository

import android.util.Log
import com.example.newsapp.data.local.database.NewsDatabase
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.mapper.toNewsEntity
import com.example.newsapp.data.mapper.toNewsList
import com.example.newsapp.data.remote.web.NewsApi
import com.example.newsapp.domain.model.NewsList
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.utils.ErrorType
import com.example.newsapp.utils.SetResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val newsApi: NewsApi,
    private val database: NewsDatabase
):NewsRepository {
    override suspend fun getNewsList(text: String?): Flow<SetResults<List<NewsList>>> {
        return flow {
            val getNewsList = database.newsDao().getNewsList()

            if (getNewsList.isNotEmpty() && text?.isBlank() == true){
                emit(SetResults.Success(
                    data = getNewsList.map {
                        it.toNewsList()
                    }
                ))

                return@flow
            }

            try {
                //If Database is Empty add Data to Database

                val getNewsApi = newsApi.getNewsApi(text = text)

                val newsEntity = getNewsApi.news?.mapNotNull {
                    it?.toNewsList()
                }.orEmpty()



                database.newsDao().clearNews()
                database.newsDao().insertNews(news = newsEntity)

                    emit(SetResults.Success(
                        data = newsEntity.map {
                            it.toNewsList()
                        }
                    ))

            }catch (e:Exception){
                emit(SetResults.Error(
                    ErrorType.UNKNOWN_ERROR,
                ))
            }
            catch (e:IOException){
                emit(SetResults.Error(
                    ErrorType.UNKNOWN_ERROR
                ))
            }

            return@flow
        }
    }

    override suspend fun getNewsId(id: Int): Flow<SetResults<NewsList>> {
        return flow {

            val getNewsById = database.newsDao().getNewsById(id)

            Log.d("IDdd","${id}")


            emit(SetResults.Success(
                data = getNewsById.toNewsList()
            ))

            if (getNewsById == null){
                emit(SetResults.Error(
                    ErrorType.UNKNOWN_ERROR,
                ))
            }
            return@flow
        }
    }

    override suspend fun onRefresh() {
        return database.newsDao().clearNews()
    }


}