package registration.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import registration.data.repository.LocationRepositoryImpl
import registration.data.repository.RegistrationRepositoryImpl
import registration.data.service.RegistrationService
import registration.data.service.RegistrationServiceImpl
import registration.domain.repository.LocationRepository
import registration.domain.repository.RegistrationRepository
import registration.domain.usecase.GetBrazilianStatesUseCase
import registration.domain.usecase.GetCountriesUseCase
import registration.domain.usecase.RegisterPlayerUseCase
import registration.presentation.viewmodel.RegistrationViewModel

val registrationModule = module {
    single<LocationRepository> { LocationRepositoryImpl() }
    single<RegistrationService> { RegistrationServiceImpl(get()) }
    single<RegistrationRepository> { RegistrationRepositoryImpl(get()) }
    singleOf(::GetCountriesUseCase)
    singleOf(::GetBrazilianStatesUseCase)
    singleOf(::RegisterPlayerUseCase)
    viewModelOf(::RegistrationViewModel)
}
