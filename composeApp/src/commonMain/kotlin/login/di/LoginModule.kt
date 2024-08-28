package login.di

import login.data.repository.LoginRepositoryImpl
import login.data.service.LoginService
import login.data.service.LoginServiceImpl
import login.domain.repository.LoginRepository
import login.domain.usecase.LoginPlayerUseCase
import login.presentation.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginModule = module {
    single<LoginService> { LoginServiceImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    singleOf(::LoginPlayerUseCase)
    viewModelOf(::LoginViewModel)
}
