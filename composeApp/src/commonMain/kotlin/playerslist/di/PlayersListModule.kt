package playerslist.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import playerslist.data.repository.PlayersListRepositoryImpl
import playerslist.data.service.PlayersListService
import playerslist.data.service.PlayersListServiceImpl
import playerslist.domain.repository.PlayersListRepository
import playerslist.domain.usecase.FetchPlayersListUseCase
import playerslist.domain.usecase.GetSegmentedPlayersListUseCase
import playerslist.presentation.PlayersListViewModel

val playersListModule = module {
    single<PlayersListService> { PlayersListServiceImpl(get()) }
    single<PlayersListRepository> { PlayersListRepositoryImpl(get(), get()) }
    singleOf(::GetSegmentedPlayersListUseCase)
    singleOf(::FetchPlayersListUseCase)
    viewModelOf(::PlayersListViewModel)
}
