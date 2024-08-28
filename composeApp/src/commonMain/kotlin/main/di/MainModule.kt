package main.di

import main.presentation.MainViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
}
