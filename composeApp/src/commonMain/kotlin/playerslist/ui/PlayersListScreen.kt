@file:OptIn(KoinExperimentalAPI::class)

package playerslist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.bottomsheet.BottomSheetManager
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.hierarchy_board
import futibasrotterdam.composeapp.generated.resources.hierarchy_monthly
import futibasrotterdam.composeapp.generated.resources.hierarchy_president
import futibasrotterdam.composeapp.generated.resources.hierarchy_spot
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import player.ui.PlayersLoadingView
import playerslist.presentation.PlayersListScreenState
import playerslist.presentation.PlayersListViewModel
import playerslist.presentation.PlayerListItemViewState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersScreen(
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel = koinViewModel<PlayersListViewModel>()

    val state = viewModel.state.collectAsState().value

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refreshPlayersList() },
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())
            .background(color = themeColors.defaultScreenBackground)
            .padding(dimensions.gap4)
        ) {
            when (state) {
                is PlayersListScreenState.Loading -> PlayersLoadingView()
                is PlayersListScreenState.Error -> {}
                is PlayersListScreenState.Content -> ContentView(
                    content = state,
                    bottomSheetManager = bottomSheetManager,
                )
            }

        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun ContentView(
    content: PlayersListScreenState.Content,
    bottomSheetManager: BottomSheetManager,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
    ) {
        PlayerListItem(
            viewState = PlayerListItemViewState(
                hierarchy = stringResource(Res.string.hierarchy_president),
                players = content.presidentList,
            ),
            bottomSheetManager = bottomSheetManager,
        )
        PlayerListItem(
            viewState = PlayerListItemViewState(
                hierarchy = stringResource(Res.string.hierarchy_board),
                players = content.boardList,
            ),
            bottomSheetManager = bottomSheetManager,
        )
        PlayerListItem(
            viewState = PlayerListItemViewState(
                hierarchy = stringResource(Res.string.hierarchy_monthly),
                players = content.monthlyList,
            ),
            bottomSheetManager = bottomSheetManager,
        )
        PlayerListItem(
            viewState = PlayerListItemViewState(
                hierarchy = stringResource(Res.string.hierarchy_spot),
                players = content.spotList,
            ),
            bottomSheetManager = bottomSheetManager,
        )
    }
}
