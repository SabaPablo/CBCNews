package com.doce.cactus.saba.cbcnews.koin

import android.app.Application
import androidx.room.Room
import com.doce.cactus.saba.cbcnews.AppDatabase
import com.doce.cactus.saba.cbcnews.apis.NewsApi
import com.doce.cactus.saba.cbcnews.dao.NewsDao
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import com.doce.cactus.saba.cbcnews.repositories.NewsRepositoryImpl
import com.doce.cactus.saba.cbcnews.ui.detail.DetailViewModel
import com.doce.cactus.saba.cbcnews.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val viewModelModule = module {
    viewModel { HomeViewModel( get() ) }
    viewModel { DetailViewModel() }
}

val repositoriesModule = module {
    single { provideNewsRepository( get(), get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofit( get() ) }
    factory { provideNewsApi( get() ) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "news")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: AppDatabase): NewsDao {
        return  database.newsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}



// Network providers

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://www.cbc.ca/aggregate_api/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

// Repositories providers

fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)