package com.doce.cactus.saba.cbcnews.repositories

import androidx.lifecycle.LiveData
import com.doce.cactus.saba.cbcnews.models.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun news(): Flow<List<News>>
    fun newsOffline(): Flow<List<News>>
    suspend fun saveNews(it: List<News>)
}