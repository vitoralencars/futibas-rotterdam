package notifications.di

import notifications.data.repository.NotificationsRepositoryImpl
import notifications.data.service.NotificationsService
import notifications.data.service.NotificationsServiceImpl
import notifications.domain.repository.NotificationsRepository
import notifications.domain.usecase.StoreFCMTokenUseCase
import notifications.domain.usecase.UpdateFCMTokenUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val notificationsModule = module {
    single<NotificationsService> { NotificationsServiceImpl(get()) }
    single<NotificationsRepository> { NotificationsRepositoryImpl(get(), get()) }
    singleOf(::UpdateFCMTokenUseCase)
    singleOf(::StoreFCMTokenUseCase)
}
