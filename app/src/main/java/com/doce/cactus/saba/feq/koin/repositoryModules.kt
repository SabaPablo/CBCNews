package com.doce.cactus.saba.feq.koin

import com.doce.cactus.saba.feq.apis.NewsApi
import com.doce.cactus.saba.feq.dao.NewsDao
import com.doce.cactus.saba.feq.repositories.NewsRepository
import com.doce.cactus.saba.feq.repositories.NewsRepositoryImpl
import com.doce.cactus.saba.feq.repositories.ShowRepository
import com.doce.cactus.saba.feq.repositories.ShowRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single { provideNewsRepository( get(), get()) }
    single { provideShowRepository( ) }
}

fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)

fun provideShowRepository(): ShowRepository = ShowRepositoryImpl()