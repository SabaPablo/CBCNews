package com.doce.cactus.saba.feq.koin

import com.doce.cactus.saba.feq.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel( get() ) }
}

