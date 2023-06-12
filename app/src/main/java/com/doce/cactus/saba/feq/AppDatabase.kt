package com.doce.cactus.saba.feq

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doce.cactus.saba.feq.dao.NewsDao
import com.doce.cactus.saba.feq.models.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
     abstract fun newsDao(): NewsDao
}