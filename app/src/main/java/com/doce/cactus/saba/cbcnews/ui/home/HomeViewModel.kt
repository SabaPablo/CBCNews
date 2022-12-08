package com.doce.cactus.saba.cbcnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doce.cactus.saba.cbcnews.models.News
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    private val _newsLiveData: MutableLiveData<List<News>> = MutableLiveData<List<News>>()
    val newsLiveData: LiveData<List<News>> = _newsLiveData

    val newsFiltered: MutableLiveData<List<News>> = MutableLiveData()




    fun getNetworkNews(){
        viewModelScope.launch {
            newsRepository.news().catch { e ->
                e.printStackTrace()
                getOfflineNews()
            }.collect {
                _newsLiveData.value = it
                saveNewsInCache(it)
            }

        }
    }

    private fun saveNewsInCache(it: List<News>) {
        CoroutineScope(Dispatchers.IO).launch {
            newsRepository.saveNews(it)
        }
    }

    fun setFilter(checkedIds: List<Int>) {
        val checkedTypes = checkedIdsToTypes(checkedIds)

        newsFiltered.value = newsLiveData.value?.filter { checkedTypes.contains(it.type) }
    }

    private fun checkedIdsToTypes(checkedIds: List<Int>): List<String> {
        return checkedIds.map { conversionTypeOfId(it) }
    }

    private fun conversionTypeOfId(it: Int): String {
        return when(it){
            1 -> "contentpackage"
            else -> "story"
        }
    }

    fun getNewsWith(hasConnection: Boolean) {
        if(hasConnection){
            getNetworkNews()
        }else{
            getOfflineNews()
        }
    }

    private fun getOfflineNews() {
        viewModelScope.launch {
            newsRepository.newsOffline().catch { e ->
                e.printStackTrace()
            }.collect {
                _newsLiveData.value = it
            }
        }
    }


}