package com.doce.cactus.saba.cbcnews.koin

import android.app.Application
import androidx.room.Room
import com.doce.cactus.saba.cbcnews.AppDatabase
import com.doce.cactus.saba.cbcnews.dao.NewsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}

fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "news")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideCountriesDao(database: AppDatabase): NewsDao {
    return  database.newsDao()
}
