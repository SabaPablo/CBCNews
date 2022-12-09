package com.doce.cactus.saba.cbcnews.ui.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RuntimeEnvironment


@RunWith(AndroidJUnit4::class)
class NewsAdapterTest{
    private var context: Context = RuntimeEnvironment.getApplication()

    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewHolder: NewsAdapter.ViewHolder

    @Before
    fun setup() {

        adapter = NewsAdapter()
        recyclerView = RecyclerView(context)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewHolder = adapter.onCreateViewHolder(recyclerView, 0)

    }

    @After
    fun endTest(){
        stopKoin()
    }

    @Test
    fun `submit a list of 10 news in the adapter and the itemCount is 10`(){
        adapter.submitList(newsListData())
        //adapter.onBindViewHolder(viewHolder, 0)

        assertThat(adapter.itemCount, Matchers.`is`(10))
    }

    @Test
    fun `bind viewHolder on position 0 and 2 and get the same title an published text of submit list`(){
        adapter.submitList(newsListData())
        adapter.onBindViewHolder(viewHolder, 0)

        assertThat(viewHolder.binding.titleTv.text, Matchers.`is`(newsListData()[0].title))
        assertThat(viewHolder.binding.publishedTv.text, Matchers.`is`("Posted: ${newsListData()[0].readablePublishedAt}"))

        adapter.onBindViewHolder(viewHolder, 2)

        assertThat(viewHolder.binding.titleTv.text, Matchers.`is`(newsListData()[2].title))
        assertThat(viewHolder.binding.publishedTv.text, Matchers.`is`("Posted: ${newsListData()[2].readablePublishedAt}"))


    }
}