package com.doce.cactus.saba.cbcnews.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doce.cactus.saba.cbcnews.getOrAwaitValue
import com.doce.cactus.saba.cbcnews.repositories.NewsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{

    @get: Rule val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun endTest(){
        stopKoin()
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `receive the news ordered by descending update`(){

        val newsRepositoryMock = mock<NewsRepository>{
            on{ news() } doReturn flowOf(newsListData())
        }
         // Will be launched in the mainThreadSurrogate dispatcher
            runBlocking {
                launch(Dispatchers.IO) {
                val viewModel= HomeViewModel(newsRepositoryMock)
                    viewModel.getNetworkNews()
                    val value = viewModel.newsLiveData.getOrAwaitValue()
                    assertThat(value.size, Matchers.`is`(10))
                    assertThat(value[0].id, Matchers.`is`(3863948))
            }
        }
    }

    @Test
    fun `gets offline news and expose in LiveData`(){

        val newsRepositoryMock = mock<NewsRepository>{
            on{ newsOffline() } doReturn flowOf(newsListData())

        }
        // Will be launched in the mainThreadSurrogate dispatcher
        runBlocking {
            launch(Dispatchers.IO) {
                val viewModel= HomeViewModel(newsRepositoryMock)
                viewModel.getCacheNews()
                val value = viewModel.newsLiveData.getOrAwaitValue()
                assertThat(value.size, Matchers.`is`(10))
                assertThat(value[0].id, Matchers.`is`(1414200))

            }
        }
    }

    @Test
    fun `gets news, and get two types(contentpackage and story)`(){

        val newsRepositoryMock = mock<NewsRepository>{
            on{ news() } doReturn flowOf(newsListData())

        }
        // Will be launched in the mainThreadSurrogate dispatcher
        runBlocking {
            launch(Dispatchers.IO) {
                val viewModel= HomeViewModel(newsRepositoryMock)
                viewModel.getNetworkNews()

                val types = viewModel.types.getOrAwaitValue()
                assertThat(types.size, Matchers.`is`(2))
                assertThat(types, Matchers.`is`(listOf("contentpackage", "story")))

            }
        }
    }

    @Test
    fun `gets news, set a filter(listOf(story)) and get 6 news filtered`(){

        val newsRepositoryMock = mock<NewsRepository>{
            on{ news() } doReturn flowOf(newsListData())

        }
        // Will be launched in the mainThreadSurrogate dispatcher
        runBlocking {
            launch(Dispatchers.IO) {
                val viewModel= HomeViewModel(newsRepositoryMock)
                viewModel.getNetworkNews()
                viewModel.types.getOrAwaitValue()
                viewModel.setFilter(listOf(1))
                val newsList = viewModel.newsFiltered.getOrAwaitValue()
                assertThat(newsList.size, Matchers.`is`(6))
            }
        }
    }
}