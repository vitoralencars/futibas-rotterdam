package components.players.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import player.domain.model.Player

@Composable
fun PlayersList(
    viewState: PlayersListViewState,
    photoSize: Dp = dimensions.playerPhotoList,
    onPlayerTapped: (Player) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        viewState.players.forEach { player ->
            with(player) {
                Column(
                    modifier = Modifier.clickable {
                        onPlayerTapped(player)
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensions.gap2)
                    ) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                setToSaturation(viewState.photoColorSaturation)
                            }),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(photoSize)
                                .clip(CircleShape)
                        )
                        Column {
                            Text(
                                text = nickname ?: name,
                                style = typographies.h1
                            )
                            if (viewState.showHierarchyLevel) {
                                Text(
                                    text = level.description,
                                    style = typographies.bodyLight
                                )
                            }
                        }
                    }
                    Divider(
                        color = themeColors.dividerColor,
                        thickness = dimensions.dividerThickness,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
