package common.di

import common.usecase.GetBrazilianStateFromStateNameUseCase
import common.usecase.GetCountryFromCountryNameUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::GetBrazilianStateFromStateNameUseCase)
    singleOf(::GetCountryFromCountryNameUseCase)
}
