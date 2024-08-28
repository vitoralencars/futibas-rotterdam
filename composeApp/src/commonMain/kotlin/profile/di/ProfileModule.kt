package profile.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import profile.data.mapper.CourtDataToDomainMapper
import profile.data.repository.ProfileRepositoryImpl
import profile.data.service.ProfileService
import profile.data.service.ProfileServiceImpl
import profile.domain.repository.ProfileRepository
import profile.domain.usecase.CreateGameListUseCase
import profile.domain.usecase.DeletePlayerAccountUseCase
import profile.domain.usecase.FetchPendingPlayersUseCase
import profile.domain.usecase.GetCourtsUseCase
import profile.domain.usecase.RemoveLoggedInPlayerUseCase
import profile.domain.usecase.UpdateGameListUseCase
import profile.domain.usecase.UpdatePlayerDataUseCase
import profile.domain.usecase.UploadPlayerPhotoUseCase
import profile.presentation.ProfileViewModel
import profile.presentation.menu.courts.CourtsViewModel
import profile.presentation.general.ProfileGeneralViewModel
import profile.presentation.menu.newgamelist.NewGameListViewModel
import profile.presentation.menu.pendingplayers.PendingPlayersViewModel
import profile.presentation.menu.personaldata.PersonalDataViewModel

val profileModule = module {
    single<ProfileService> { ProfileServiceImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get(), get()) }
    singleOf(::CourtDataToDomainMapper)
    singleOf(::UpdatePlayerDataUseCase)
    singleOf(::UploadPlayerPhotoUseCase)
    singleOf(::CreateGameListUseCase)
    singleOf(::GetCourtsUseCase)
    singleOf(::FetchPendingPlayersUseCase)
    singleOf(::UpdateGameListUseCase)
    singleOf(::RemoveLoggedInPlayerUseCase)
    singleOf(::DeletePlayerAccountUseCase)
    viewModelOf(::ProfileGeneralViewModel)
    viewModelOf(::PersonalDataViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::CourtsViewModel)
    viewModelOf(::NewGameListViewModel)
    viewModelOf(::PendingPlayersViewModel)
}
