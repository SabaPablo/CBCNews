package com.doce.cactus.saba.cbcnews.apis

import com.doce.cactus.saba.cbcnews.models.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v1/items")
    suspend fun news(@Query("lineupSlug") lineupSlug: String): List<News>

}