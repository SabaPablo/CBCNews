package com.doce.cactus.saba.cbcnews.repositories

import com.doce.cactus.saba.cbcnews.apis.NewsApi
import com.doce.cactus.saba.cbcnews.models.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(private val api: NewsApi): NewsRepository {
    override fun news(): Flow<List<News>> {
        return flow {
            emit(api.news("news"))
        }.flowOn(Dispatchers.IO)
    }
}