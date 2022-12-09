package com.doce.cactus.saba.cbcnews.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(

    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("language")
    val language: String = "",
    @SerializedName("publishedAt")
    val publishedAt: Long = 0,
    @SerializedName("readablePublishedAt")
    val readablePublishedAt: String = "",
    @SerializedName("readableUpdatedAt")
    val readableUpdatedAt: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("updatedAt")
    val updatedAt: Long = 0,
    @SerializedName("version")
    val version: String = "",
    @SerializedName("images")
    @Embedded val images: Images = Images()
)