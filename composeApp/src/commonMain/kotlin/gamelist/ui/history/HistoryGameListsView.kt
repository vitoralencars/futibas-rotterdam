package gamelist.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.bottomsheet.BottomSheetManager
import common.ui.extension.shimmerEffect
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import gamelist.presentation.model.GameListTabViewState
import gamelist.presentation.model.history.HistoryGameListItemViewState

@Composable
internal fun HistoryGameListsView(
    viewState: GameListTabViewState.HistoryGameListsViewState,
    bottomSheetManager: BottomSheetManager,
    onListItemTapped: (HistoryGameListItemViewState) -> Unit,
) {
    when (viewState) {
        is GameListTabViewState.HistoryGameListsViewState.Loading -> LoadingLists()
        is GameListTabViewState.HistoryGameListsViewState.Content -> {
            ContentLists(
                listItems = viewState.historyListViewStates,
                bottomSheetManager = bottomSheetManager,
                onListItemTapped = { listItem ->
                    onListItemTapped(listItem)
                }
            )
        }
        else -> {}
    }
}

@Composable
private fun ContentLists(
    listItems: List<HistoryGameListItemViewState>,
    bottomSheetManager: BottomSheetManager,
    onListItemTapped: (HistoryGameListItemViewState) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = dimensions.gap4,
                end = dimensions.gap4,
                bottom = dimensions.gap4,
            )
    ) {
        listItems.forEach { listItem ->
            HistoryGameListItem(
                viewState = listItem,
                bottomSheetManager = bottomSheetManager,
                onListTapped = {
                    onListItemTapped(listItem)
                }
            )
        }
    }
}

@Composable
private fun LoadingLists() {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensions.gap4,
                end = dimensions.gap4,
                bottom = dimensions.gap4,
            )
    ) {
        repeat(10) {
            Card(
                shape = RoundedCornerShape(dimensions.cardRoundedCorner),
                elevation = dimensions.cardElevation,
                backgroundColor = themeColors.cardBackground,
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
                    .padding(dimensions.gap4)
                    .align(Alignment.Start)
                ) {
                    Box(modifier = Modifier.height(dimensions.gap4).width(104.dp).shimmerEffect())
                }
            }
        }
    }
}
