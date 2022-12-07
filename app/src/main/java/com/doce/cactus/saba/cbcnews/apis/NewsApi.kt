package com.doce.cactus.saba.cbcnews.apis

import com.doce.cactus.saba.cbcnews.models.News
import retrofit2.http.GET

interface NewsApi {

    @GET("v1/items?lineupSlug=news")
    suspend fun news(): List<News>

}