package com.doce.cactus.saba.cbcnews.koin

import com.doce.cactus.saba.cbcnews.apis.NewsApi
import com.doce.cactus.saba.cbcnews.dao.NewsDao
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import com.doce.cactus.saba.cbcnews.repositories.NewsRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single { provideNewsRepository( get(), get()) }
}

fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)