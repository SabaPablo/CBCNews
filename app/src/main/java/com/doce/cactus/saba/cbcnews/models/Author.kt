package com.doce.cactus.saba.cbcnews.models


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("display")
    val display: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = ""
)