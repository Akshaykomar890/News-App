package com.example.newsapp.presentation.details

import android.net.NetworkInfo.DetailedState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepositoryImp
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


}