package gamelist.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import components.bottomsheet.BottomSheetManager
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.players.detail.PlayerDetail
import components.players.detail.PlayerDetailViewState
import components.players.list.PlayersList
import components.players.list.PlayersListViewState
import gamelist.presentation.model.history.HistoryGameListItemViewState
import player.ui.PlayersLoadingView

@Composable
internal fun HistoryGameListItem(
    viewState: HistoryGameListItemViewState,
    bottomSheetManager: BottomSheetManager,
    onListTapped: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        elevation = dimensions.cardElevation,
        backgroundColor = themeColors.cardBackground,
        modifier = Modifier
            .clip(RoundedCornerShape(dimensions.cardRoundedCorner))
            .clickable {
                onListTapped()
            }
    ) {
        Column {
            Row(
                modifier = Modifier.padding(dimensions.gap4) ,
            ) {
                Text(
                    text = viewState.historyGameItem.date,
                    style = typographies.h2,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    tint = themeColors.dividerColor,
                    modifier = Modifier.graphicsLayer(
                        rotationZ = if (viewState is HistoryGameListItemViewState.HiddenList) 0F else 180F
                    ),
                )
            }

            if (viewState is HistoryGameListItemViewState.ExpandedList) {
                when (viewState) {
                    is HistoryGameListItemViewState.ExpandedList.Loading -> PlayersLoadingView(
                        cardElevation = dimensions.gap0,
                    )
                    is HistoryGameListItemViewState.ExpandedList.Content -> {
                        PlayersList(
                            viewState = PlayersListViewState(players = viewState.players),
                            photoSize = dimensions.playerPhotoHistoryList,
                            onPlayerTapped = { player ->
                                bottomSheetManager.show {
                                    PlayerDetail(PlayerDetailViewState(player = player))
                                }
                            },
                            modifier = Modifier.padding(dimensions.gap4),
                        )
                    }
                }
            }
        }
    }
}
