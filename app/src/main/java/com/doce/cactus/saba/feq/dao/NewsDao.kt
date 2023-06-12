package com.doce.cactus.saba.feq.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doce.cactus.saba.feq.models.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: News)

    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    suspend fun getAll(): List<News>

    @Query("DELETE FROM news")
    fun deleteAll()
}