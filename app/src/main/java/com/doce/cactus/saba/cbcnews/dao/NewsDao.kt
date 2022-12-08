package com.doce.cactus.saba.cbcnews.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doce.cactus.saba.cbcnews.models.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: News)

    @Query("SELECT * FROM news")
    suspend fun getAll(): List<News>


    @Query("DELETE FROM news")
    fun deleteAll()
}