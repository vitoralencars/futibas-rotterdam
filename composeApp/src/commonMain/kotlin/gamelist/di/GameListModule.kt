package gamelist.di

import gamelist.data.mapper.HistoryGameListsDataToHistoryGameListsDomainMapper
import gamelist.data.mapper.PlayerDataListToCurrentGameListCategoryMapper
import gamelist.data.repository.GameListRepositoryImpl
import gamelist.data.service.GameListService
import gamelist.data.service.GameListServiceImpl
import gamelist.domain.repository.GameListRepository
import gamelist.domain.usecase.FetchCurrentGameListUseCase
import gamelist.domain.usecase.FetchHistoryListsUseCase
import gamelist.domain.usecase.UpdatePlayerListStatusUseCase
import gamelist.presentation.viewmodel.GameListViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gameListModule = module {
    single<GameListService> { GameListServiceImpl(get()) }
    single<GameListRepository> { GameListRepositoryImpl(get(), get(), get()) }
    singleOf(::FetchCurrentGameListUseCase)
    singleOf(::FetchHistoryListsUseCase)
    singleOf(::UpdatePlayerListStatusUseCase)
    singleOf(::PlayerDataListToCurrentGameListCategoryMapper)
    singleOf(::HistoryGameListsDataToHistoryGameListsDomainMapper)
    viewModelOf(::GameListViewModel)
}
