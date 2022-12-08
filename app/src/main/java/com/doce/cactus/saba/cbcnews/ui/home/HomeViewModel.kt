package com.doce.cactus.saba.cbcnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doce.cactus.saba.cbcnews.models.News
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    private val _newsLiveData: MutableLiveData<List<News>> = MutableLiveData<List<News>>()
    val newsLiveData: LiveData<List<News>> = _newsLiveData
    val newsFiltered: MutableLiveData<List<News>> = MutableLiveData()
    val types:  MutableLiveData<List<String>> = MutableLiveData()

    val eventsChannel = Channel<HomeEvents>(Channel.UNLIMITED)
    val events = eventsChannel.receiveAsFlow()

    fun getNetworkNews(){
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.news().onStart {
                // Loading
            }.catch { e ->
                e.printStackTrace()
                getOfflineNews()
                eventsChannel.send(HomeEvents.ErrorNews)

            }.collect {
                _newsLiveData.postValue(it.sortedBy { news ->  news.updatedAt })
                saveNewsInCache(it)
            }
        }
    }

    private fun saveNewsInCache(it: List<News>) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAll()
            newsRepository.saveNews(it)
        }
    }

    fun setFilter(checkedIds: List<Int>) {
        val checkedTypes = checkedIdsToTypes(checkedIds)
        newsFiltered.value = newsLiveData.value?.filter { checkedTypes.contains(it.type) }?.sortedBy { news -> news.updatedAt }
    }

    private fun checkedIdsToTypes(checkedIds: List<Int>): List<String> {
        return checkedIds.map { conversionTypeOfId(it) }
    }

    private fun conversionTypeOfId(it: Int): String {
        return types.value!![it]
    }

    private suspend fun getOfflineNews() {
            newsRepository.newsOffline().catch { e ->
                e.printStackTrace()
            }.collect { listNews ->
                _newsLiveData.postValue(listNews.sortedBy { news -> news.updatedAt })
            }
    }

    fun setChips(news: List<News>?) {
        types.value = news?.map { it.type }?.distinct()

    }
}

sealed class HomeEvents {
    object ErrorNews : HomeEvents()
}