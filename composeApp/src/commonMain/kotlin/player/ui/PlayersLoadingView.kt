package player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import common.ui.extension.shimmerEffect
import common.ui.theme.dimensions
import common.ui.theme.themeColors

@Composable
fun PlayersLoadingView(
    cardElevation: Dp = dimensions.cardElevation,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = cardElevation,
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        backgroundColor = themeColors.cardBackground,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(dimensions.gap4)
        ) {
            repeat(15) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.gap2)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimensions.playerPhotoList)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Box(modifier = Modifier
                        .height(dimensions.gap4)
                        .weight(1f)
                        .shimmerEffect()
                    )
                }
            }
        }
    }
}
