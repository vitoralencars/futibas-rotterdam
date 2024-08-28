package splash.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import splash.presentation.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
}
