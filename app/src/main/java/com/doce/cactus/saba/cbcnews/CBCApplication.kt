package com.doce.cactus.saba.cbcnews

import android.app.Application
import android.content.Context
import com.doce.cactus.saba.cbcnews.koin.databaseModule
import com.doce.cactus.saba.cbcnews.koin.networkModule
import com.doce.cactus.saba.cbcnews.koin.repositoriesModule
import com.doce.cactus.saba.cbcnews.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CBCApplication: Application() {

    init {
        instance = this
    }

    companion object {
        var instance: CBCApplication? = null
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@CBCApplication)
            modules(listOf(
                viewModelModule, networkModule, repositoriesModule, databaseModule
            ))
        }
    }

}