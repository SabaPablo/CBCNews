package com.doce.cactus.saba.feq

import android.app.Application
import android.content.Context
import com.doce.cactus.saba.feq.koin.databaseModule
import com.doce.cactus.saba.feq.koin.networkModule
import com.doce.cactus.saba.feq.koin.repositoriesModule
import com.doce.cactus.saba.feq.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FEQApplication: Application() {

    init {
        instance = this
    }

    companion object {
        var instance: FEQApplication? = null
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FEQApplication)
            modules(listOf(
                viewModelModule, networkModule, repositoriesModule, databaseModule
            ))
        }
    }

}