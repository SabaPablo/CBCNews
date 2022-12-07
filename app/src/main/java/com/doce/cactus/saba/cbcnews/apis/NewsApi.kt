package com.doce.cactus.saba.cbcnews.apis

import retrofit2.http.GET

interface NewsApi {

    @GET
    suspend fun news()

}