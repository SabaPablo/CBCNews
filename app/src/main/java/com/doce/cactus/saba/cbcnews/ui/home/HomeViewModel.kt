package com.doce.cactus.saba.cbcnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doce.cactus.saba.cbcnews.models.News
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    private val _newsLiveData: MutableLiveData<List<News>> = MutableLiveData<List<News>>()
    val newsLiveData: LiveData<List<News>> = _newsLiveData

    fun getNews(){
        viewModelScope.launch {
            newsRepository.news().catch { e ->
                e.printStackTrace()
            }.collect {
                _newsLiveData.value = it
            }

        }
    }


}