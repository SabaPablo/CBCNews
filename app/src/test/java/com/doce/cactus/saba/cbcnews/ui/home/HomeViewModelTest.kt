package com.doce.cactus.saba.cbcnews.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doce.cactus.saba.cbcnews.models.News
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @After
    fun endTest(){
        stopKoin()
    }

    @Test
    fun getNewsTest(){

        val newsRepositoryMock = mock<NewsRepository>{
            on{ news() } doReturn flow<List<News>> { listOf(News(title = "aTitle")) }.flowOn(Dispatchers.IO)
        }

        val viewModel= HomeViewModel(newsRepositoryMock)
        val observer = Observer<List<News>>{}
        try {
            viewModel.newsLiveData.observeForever(observer)
            viewModel.getNews()
            assertThat(viewModel.newsLiveData.value?.size, Matchers.`is`(1))
        } finally {
            viewModel.newsLiveData.removeObserver(observer)
        }
    }

}