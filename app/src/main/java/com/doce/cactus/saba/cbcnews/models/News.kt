package com.doce.cactus.saba.cbcnews.models


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("draft")
    val draft: Boolean = false,
    @SerializedName("embedTypes")
    val embedTypes: Any = Any(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("images")
    val images: Images = Images(),
    @SerializedName("language")
    val language: String = "",
    @SerializedName("publishedAt")
    val publishedAt: Long = 0,
    @SerializedName("readablePublishedAt")
    val readablePublishedAt: String = "",
    @SerializedName("readableUpdatedAt")
    val readableUpdatedAt: String = "",
    @SerializedName("source")
    val source: String = "",
    @SerializedName("sourceId")
    val sourceId: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("typeAttributes")
    val typeAttributes: TypeAttributes = TypeAttributes(),
    @SerializedName("updatedAt")
    val updatedAt: Long = 0,
    @SerializedName("version")
    val version: String = ""
)