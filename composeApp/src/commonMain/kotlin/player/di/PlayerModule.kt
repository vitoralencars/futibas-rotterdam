package player.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import player.data.repository.PlayerRepositoryImpl
import player.domain.repository.PlayerRepository
import player.domain.usecase.RetrieveLoggedInPlayerUseCase
import player.domain.usecase.StoreLoggedInPlayerUseCase
import player.data.mapper.PlayerDataToPlayerDomainMapper
import player.data.service.PlayerService
import player.data.service.PlayerServiceImpl
import player.domain.usecase.FetchPlayersUseCase
import player.domain.usecase.UpdatePlayerLevelUseCase

val playerModule = module {
    single<PlayerService> { PlayerServiceImpl(get()) }
    single<PlayerRepository> { PlayerRepositoryImpl(get(), get(), get()) }
    singleOf(::StoreLoggedInPlayerUseCase)
    singleOf(::RetrieveLoggedInPlayerUseCase)
    singleOf(::FetchPlayersUseCase)
    singleOf(::UpdatePlayerLevelUseCase)
    singleOf(::PlayerDataToPlayerDomainMapper)
}
