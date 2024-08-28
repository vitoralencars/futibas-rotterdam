package player.data.mapper

import common.usecase.GetBrazilianStateFromStateNameUseCase
import common.usecase.GetCountryFromCountryNameUseCase
import common.util.extension.orEmpty
import player.data.model.PlayerData
import player.domain.mapper.toPLayerLevel
import player.domain.model.Player

class PlayerDataToPlayerDomainMapper(
    private val getBrazilianStateFromStateName: GetBrazilianStateFromStateNameUseCase,
    private val getCountryFromCountryName: GetCountryFromCountryNameUseCase,
) {
    suspend operator fun invoke(playerData: PlayerData): Player =
        with(playerData) {
            val formattedCity = state?.let {
                "$city - ${getBrazilianStateFromStateName(it)?.abbreviation.orEmpty()}"
            } ?: city

            Player(
                playerId = playerId,
                name = name,
                nickname = nickname,
                birthday = birthday,
                age = age,
                level = level.toPLayerLevel(),
                city = formattedCity,
                photoUrl = photoUrl,
                favoriteTeam = favoriteTeam,
                country = getCountryFromCountryName(country).orEmpty(),
            )
        }
}
