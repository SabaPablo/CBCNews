package com.doce.cactus.saba.feq.repositories

import com.doce.cactus.saba.feq.apis.NewsApi
import com.doce.cactus.saba.feq.dao.NewsDao
import com.doce.cactus.saba.feq.models.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(private val api: NewsApi, private  var dao: NewsDao,): NewsRepository {
    override fun news(): Flow<List<News>> {
        return flow {
            emit(api.news("news"))
        }.flowOn(Dispatchers.IO)
    }

    override fun newsOffline(): Flow<List<News>>{
        return flow {
            emit(dao.getAll())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveNews(it: List<News>) {
        dao.insertAll(*it.toTypedArray())
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

}