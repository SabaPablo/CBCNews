package com.doce.cactus.saba.cbcnews.repositories

import com.doce.cactus.saba.cbcnews.models.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun news(): Flow<List<News>>
}