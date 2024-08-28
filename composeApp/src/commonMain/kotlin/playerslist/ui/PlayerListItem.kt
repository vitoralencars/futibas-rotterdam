package playerslist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.bottomsheet.BottomSheetManager
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.players.list.PlayersList
import components.players.list.PlayersListViewState
import components.players.detail.PlayerDetail
import components.players.detail.PlayerDetailViewState
import playerslist.presentation.PlayerListItemViewState

@Composable
fun PlayerListItem(
    viewState: PlayerListItemViewState,
    bottomSheetManager: BottomSheetManager,
) {
    Card(
        elevation = dimensions.cardElevation,
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        backgroundColor = themeColors.cardBackground,
    ) {
        Column {
            Text(
                text = viewState.hierarchy,
                style = typographies.h2,
                modifier = Modifier.padding(dimensions.gap3),
            )
            PlayersList(
                viewState = PlayersListViewState(
                    players = viewState.players,
                ),
                onPlayerTapped = { player ->
                    bottomSheetManager.show {
                        PlayerDetail(viewState = PlayerDetailViewState(player))
                    }
                }
            )
        }
    }
}
