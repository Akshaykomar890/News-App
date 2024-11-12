package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepositoryImp
import com.example.newsapp.utils.ErrorType
import com.example.newsapp.utils.SetResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepositoryImp: NewsRepositoryImp
): ViewModel(){

    private val _newsListState = MutableStateFlow(NewsState())
    var newsListState = _newsListState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading  = _isLoading.asStateFlow()

    var job:Job? = null

    init {
        getNews("")
    }

     fun getNews(text:String?) {
         job?.cancel()
        job = viewModelScope.launch {
            _isLoading.value = true
            newsRepositoryImp.getNewsList(text).collectLatest {
                result->
                when(result){
                    is SetResults.Success -> {
                        result.data?.let {
                            data->
                            _newsListState.update {
                                it.copy(newsList = data)
                            }
                        }
                        _isLoading.value = false
                    }
                    is SetResults.Error -> {
                        result.errorType?.let {
                            errorData->
                          val errorMessage =  when(errorData){
                                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                                ErrorType.AUTHENTICATION_ERROR ->"Authentication failed. Please log in again."
                                ErrorType.UNKNOWN_ERROR ->  "An unknown error occurred. Please try again."

                          }
                            _newsListState.update {
                                it.copy(errorMessage = errorMessage)
                            }
                            _isLoading.value = false
                        }


                    }
                }

            }





        }
    }


}



