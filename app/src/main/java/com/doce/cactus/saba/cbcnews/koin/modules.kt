package com.doce.cactus.saba.cbcnews.koin

import com.doce.cactus.saba.cbcnews.apis.NewsApi
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import com.doce.cactus.saba.cbcnews.repositories.NewsRepositoryImpl
import com.doce.cactus.saba.cbcnews.ui.detail.DetailViewModel
import com.doce.cactus.saba.cbcnews.ui.home.HomeViewModel
import okhttp3.OkHttpClient
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
    single { provideNewsRepository( get() ) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofit( get() ) }
    factory { provideNewsApi( get() ) }
}

// Network providers

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://www.cbc.ca/aggregate_api/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

// Repositories providers

fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)