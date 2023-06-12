package com.doce.cactus.saba.feq.repositories

import com.doce.cactus.saba.feq.models.Show
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    fun getShows(): Flow<List<Show>>
}