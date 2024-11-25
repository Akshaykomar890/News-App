package com.example.newsapp.presentation.details

import android.net.NetworkInfo.DetailedState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.model.NewsEntityList
import com.example.newsapp.data.mapper.toBookmarkNewsEntityList
import com.example.newsapp.data.mapper.toNewsList
import com.example.newsapp.data.repository.NewsRepositoryImp
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.model.NewsBookmarkList
import com.example.newsapp.domain.model.NewsList
import com.example.newsapp.presentation.bookmark.BookmarkState
import com.example.newsapp.utils.ErrorType
import com.example.newsapp.utils.SetResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor  (
    private val newsRepositoryImp: NewsRepositoryImp
):ViewModel()
{
    private val _newsIdState = MutableStateFlow(DetailsState())
    val newsIdState = _newsIdState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _newsIdState.asStateFlow()

    private val _newsBookmarkState = MutableStateFlow(BookmarkState())

    val newsBookmarkState = _newsBookmarkState.asStateFlow()


    fun getNewsId(id:Int) {
        viewModelScope.launch {
            _isLoading.value = true

            newsRepositoryImp.getNewsId(id = id).collectLatest {
                result->
                when(result){
                    is SetResults.Error -> {
                        result.errorType?.let {
                            errors->
                           val error = when(errors){
                                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                                ErrorType.AUTHENTICATION_ERROR -> "Authentication failed. Please log in again."
                                ErrorType.UNKNOWN_ERROR ->  "An unknown error occurred. Please try again."
                            }

                            _newsIdState.update {
                                it.copy(errorMessage = error)
                            }
                            _isLoading.value = false
                        }

                    }
                    is SetResults.Success -> {
                        result.data?.let {
                            data->
                            _newsIdState.update {
                                it.copy(newsId = data)
                            }
                        }
                    }
                }
            }

        }
    }


    fun addToBookmark(news:NewsBookmarkList){
        viewModelScope.launch {
            newsRepositoryImp.addBookmark(news)
        }
    }
    fun getBookmarkById(id:Int){
        viewModelScope.launch {
            newsRepositoryImp.getNewBookmarkById(id).collectLatest {
                    collect->
                when(collect){
                    is SetResults.Error -> {
                        Log.d("BookmarkData","BookmarkDataNotFound")
                    }
                    is SetResults.Success ->{
                        collect.data?.let {
                                data->
                            _newsIdState.update {
                                it.copy(
                                    newsId = data.toNewsList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteBookmark(id: Int){
        viewModelScope.launch {
            newsRepositoryImp.deleteNewsBookmarkById(id)
        }
    }





}