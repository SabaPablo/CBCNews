package com.doce.cactus.saba.cbcnews

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doce.cactus.saba.cbcnews.dao.NewsDao
import com.doce.cactus.saba.cbcnews.models.News

@Database(entities = [News::class,], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
     abstract fun newsDao(): NewsDao



}