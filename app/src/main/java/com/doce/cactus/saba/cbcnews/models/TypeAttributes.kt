package com.doce.cactus.saba.cbcnews.models


import com.google.gson.annotations.SerializedName

data class TypeAttributes(
    @SerializedName("author")
    val author: Author = Author(),
    @SerializedName("imageAspects")
    val imageAspects: String = "",
    @SerializedName("imageLarge")
    val imageLarge: String = "",
    @SerializedName("imageSmall")
    val imageSmall: String = "",
    @SerializedName("trending")
    val trending: Trending = Trending()
)