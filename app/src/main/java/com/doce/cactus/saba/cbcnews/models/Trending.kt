package com.doce.cactus.saba.cbcnews.models


import com.google.gson.annotations.SerializedName

data class Trending(
    @SerializedName("numViewers")
    val numViewers: Int = 0,
    @SerializedName("numViewersSRS")
    val numViewersSRS: Int = 0
)