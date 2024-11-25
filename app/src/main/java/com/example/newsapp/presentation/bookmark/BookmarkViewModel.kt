package com.example.newsapp.presentation.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepositoryImp
import com.example.newsapp.destination.Bookmark
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.utils.SetResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor (
   private val newsRepository: NewsRepositoryImp
):ViewModel(){

    private val _newsBookmarkState = MutableStateFlow(BookmarkState())

    val newsBookmarkState = _newsBookmarkState.asStateFlow()

    init {
        getBookmarkNews()
    }


    fun getBookmarkById(id:Int){
        viewModelScope.launch {
            newsRepository.getNewBookmarkById(id).collectLatest {
                    collect->
                when(collect){
                    is SetResults.Error -> {
                        Log.d("BookmarkData","BookmarkDataNotFound")
                    }
                    is SetResults.Success ->{
                        collect.data?.let {
                                data->
                            _newsBookmarkState.update {
                                it.copy(bookmarkData = data)
                            }
                        }
                    }
                }
            }
        }
    }


    fun getBookmarkNews(){
       viewModelScope.launch {
           newsRepository.getBookmarkList().collectLatest {
               collect->
               when(collect){
                   is SetResults.Error -> {
                       Log.d("BookmarkData","BookmarkDataNotFound")
                   }
                   is SetResults.Success ->{
                       collect.data?.let {
                           data->
                           _newsBookmarkState.update {
                               it.copy(newsBookmark = data)
                           }
                       }
                   }
               }
           }
       }
    }






}