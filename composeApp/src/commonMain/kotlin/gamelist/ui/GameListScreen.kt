@file:OptIn(KoinExperimentalAPI::class)

package gamelist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import components.bottomsheet.BottomSheetManager
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.dialog.BinaryDialogClick
import gamelist.domain.model.current.GameListPlayerStatus
import gamelist.presentation.model.GameListScreenState
import gamelist.presentation.model.GameListTabViewState
import gamelist.presentation.viewmodel.GameListViewModel
import gamelist.ui.current.CurrentListsView
import gamelist.ui.history.HistoryGameListsView
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import player.ui.PlayersLoadingView

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameListScreen(
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel: GameListViewModel = koinViewModel<GameListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.onViewResumed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(themeColors.mainColor.copy(alpha = .2f))
    ) {
        when (val screenState = viewModel.state.collectAsState().value) {
            is GameListScreenState.Loading -> PlayersLoadingView(
                modifier = Modifier.padding(dimensions.gap4),
            )
            is GameListScreenState.Content -> ContentView(
                content = screenState,
                bottomSheetManager = bottomSheetManager,
                viewModel = viewModel,
                onTabSelected = { index ->
                    viewModel.onTabSelected(index)
                },
            )
        }
    }
}

@Composable
private fun ContentView(
    content: GameListScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    viewModel: GameListViewModel,
    onTabSelected:(Int) -> Unit
) {
    val selectedTabIndex = content.selectedTabIndex
    val tabs = arrayListOf<GameListTabViewState>(content.historyGameListsViewState)

    if (content.currentGameListViewState is GameListTabViewState.CurrentGameListViewState.Content) {
        tabs.add(0, content.currentGameListViewState)
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {},
        divider = {},
        modifier = Modifier
            .padding(dimensions.gap4)
            .clip(RoundedCornerShape(dimensions.tabRowRoundedCorner))
    ) {
        tabs.forEachIndexed { index, tabViewState ->
            val isSelected = selectedTabIndex == index
            Text(
                text = tabViewState.tabTitle,
                textAlign = TextAlign.Center,
                style = if (isSelected) typographies.tabSelected else typographies.tabNeutral,
                modifier = Modifier
                    .background(if(isSelected) themeColors.mainColor else themeColors.cardBackground)
                    .padding(dimensions.gap3)
                    .clickable {
                        onTabSelected(index)
                    }
            )
        }
    }

    when (val selectedTab = tabs[selectedTabIndex]) {
        is GameListTabViewState.CurrentGameListViewState -> CurrentListsView(
            viewState = selectedTab,
            onInButtonClick = {
                viewModel.updateLoggedInPlayerListStatus(GameListPlayerStatus.IN)
            },
            onOutButtonClick = {
                viewModel.updateOutAlertDialogVisibility()
            },
            onRefresh = {
                viewModel.refreshCurrentGameList()
            },
            onDialogButtonClick = {
                if (it == BinaryDialogClick.CONFIRM) {
                    viewModel.updateLoggedInPlayerListStatus(GameListPlayerStatus.OUT)
                }
                viewModel.updateOutAlertDialogVisibility()
            },
            bottomSheetManager = bottomSheetManager,
        )
        is GameListTabViewState.HistoryGameListsViewState -> {
            viewModel.fetchHistoryGameLists()

            HistoryGameListsView(
                viewState = selectedTab,
                bottomSheetManager = bottomSheetManager,
                onListItemTapped = { listItem ->
                    viewModel.onHistoryGameListItemTapped(
                        item = listItem,
                    )
                }
            )
        }
    }
}
