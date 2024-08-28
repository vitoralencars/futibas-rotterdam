package components.players.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import player.domain.model.PlayerLevel
import common.ui.theme.dimensions
import common.ui.theme.typographies

@Composable
fun PlayerDetail(viewState: PlayerDetailViewState) {
    with(viewState.player) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
            modifier = Modifier.fillMaxWidth().padding(dimensions.gap4),
        ) {
            Photo(photoUrl = photoUrl)
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensions.gap2)
            ) {
                Name(
                    name = name,
                    nickname = nickname,
                )
                Birthday(
                    birthday = birthday,
                    age = age,
                )
                SourcePlace(
                    city = city,
                    countryFlag = country.flag,
                )
                FavoriteTeam(
                    favoriteTeam = favoriteTeam,
                )
                if (viewState.shouldShowHierarchy) {
                    Hierarchy(
                        playerLevel = level,
                    )
                }
            }
        }
    }
}

@Composable
private fun Photo(photoUrl: String?) {
    AsyncImage(
        model = photoUrl,
        contentDescription = null,
        modifier = Modifier
            .size(dimensions.playerPhotoList)
            .clip(CircleShape)
    )
}

@Composable
private fun Name(
    name: String,
    nickname: String?,
) {
    val mainNameInfo = nickname ?: name

    Column {
        Text(
            text = mainNameInfo,
            style = typographies.h1
        )
        nickname?.let {
            Text(
                text = name,
                style = typographies.bodyLight
            )
        }
    }
}

@Composable
private fun Birthday(
    birthday: String,
    age: Int,
) {
    Row {
        Topic(
            title = "Idade",
            info = age.toString(),
            modifier = Modifier.weight(1f),
        )
        Topic(
            title = "Aniversário",
            info = birthday,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SourcePlace(
    city: String,
    countryFlag: String,
) {
    Topic(
        title = "Origem",
        info = city,
        infoEmoji = countryFlag,
    )
}

@Composable
private fun FavoriteTeam(
    favoriteTeam: String,
) {
    Topic(
        title = "Time do coração",
        info = favoriteTeam,
    )
}

@Composable
private fun Hierarchy(
    playerLevel: PlayerLevel
) {
    Topic(
        title = "Hierarquia",
        info = playerLevel.description,
    )
}

@Composable
private fun Topic(
    title: String,
    info: String,
    infoEmoji: String? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = typographies.bodyLight
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            infoEmoji?.let {
                Text(text = it)
                Spacer(Modifier.width(dimensions.gap1))
            }
            Text(
                text = info,
                style = typographies.bodyBold
            )
        }
    }
}
