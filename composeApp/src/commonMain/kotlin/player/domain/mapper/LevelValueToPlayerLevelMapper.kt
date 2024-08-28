package player.domain.mapper

import player.domain.model.PlayerLevel

fun Int.toPLayerLevel(): PlayerLevel {
    PlayerLevel.entries.forEach {
        if (this == it.indicator) return it
    }
    return PlayerLevel.PENDING
}
