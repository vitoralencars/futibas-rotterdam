package gamelist.ui.current

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.bottomsheet.BottomSheetManager
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.button.ButtonViewState
import components.button.PrimaryButton
import components.button.NegativeButton
import components.dialog.BinaryDialog
import components.dialog.BinaryDialogClick
import components.dialog.BinaryDialogViewState
import components.players.list.PlayersList
import components.players.list.PlayersListViewState
import components.players.detail.PlayerDetailViewState
import components.players.detail.PlayerDetail
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.game_list_confirm_out_alert
import futibasrotterdam.composeapp.generated.resources.game_list_max_players_info
import futibasrotterdam.composeapp.generated.resources.no_button
import futibasrotterdam.composeapp.generated.resources.yes_button
import gamelist.domain.model.current.CurrentGameList
import gamelist.domain.model.current.CurrentGameListCategory
import gamelist.domain.model.current.CurrentGameListType
import gamelist.presentation.model.GameListTabViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CurrentListsView(
    viewState: GameListTabViewState.CurrentGameListViewState,
    onRefresh: () -> Unit,
    onInButtonClick: () -> Unit,
    onOutButtonClick: () -> Unit,
    onDialogButtonClick: (BinaryDialogClick) -> Unit,
    bottomSheetManager: BottomSheetManager,
) {
    when (viewState) {
        is GameListTabViewState.CurrentGameListViewState.Content -> Content(
            state = viewState,
            onRefresh = onRefresh,
            onInButtonClick = onInButtonClick,
            onOutButtonClick = onOutButtonClick,
            onDialogButtonClick = onDialogButtonClick,
            bottomSheetManager = bottomSheetManager,
        )
        else -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: GameListTabViewState.CurrentGameListViewState.Content,
    onRefresh: () -> Unit,
    onInButtonClick: () -> Unit,
    onOutButtonClick: () -> Unit,
    onDialogButtonClick: (BinaryDialogClick) -> Unit,
    bottomSheetManager: BottomSheetManager,
) {
    val pullRefreshState = rememberPullRefreshState(state.isRefreshing, onRefresh)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = dimensions.gap4,
                        end = dimensions.gap4,
                    )
                    .weight(1f),
            ) {
                GameInfo(currentGameList = state.currentGameList)

                CurrentGameList(
                    listCategory = state.currentGameList.playersIn,
                    bottomSheetManager = bottomSheetManager,
                )
                CurrentGameList(
                    listCategory = state.currentGameList.playersSpot,
                    bottomSheetManager = bottomSheetManager,
                )
                CurrentGameList(
                    listCategory = state.currentGameList.playersOut,
                    bottomSheetManager = bottomSheetManager,
                )

                Spacer(Modifier.height(dimensions.gap0))
            }
            PlayerListStatusButtons(
                state = state,
                onInButtonClick = onInButtonClick,
                onOutButtonClick = onOutButtonClick,
            )
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if(state.shouldShowOutConfirmDialog) {
            BinaryDialog(
                viewState = BinaryDialogViewState(
                    confirmButtonText = stringResource(Res.string.yes_button),
                    denyButtonText = stringResource(Res.string.no_button),
                ),
                onClick = onDialogButtonClick,
            ) {
                Text(
                    text = stringResource(Res.string.game_list_confirm_out_alert),
                    style = typographies.bodyPlus,
                )
            }
        }
    }
}

@Composable
private fun GameInfo(
    currentGameList: CurrentGameList
) {
    Card(
        elevation = dimensions.cardElevation,
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        backgroundColor = themeColors.cardBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensions.gap4)
        ) {
            GameInfoData(currentGameList.location)
            GameInfoData(currentGameList.time)
            GameInfoData(stringResource(
                Res.string.game_list_max_players_info,
                currentGameList.maxPlayers.toString(),
            ))
        }
    }
}

@Composable
private fun GameInfoData(
    info: String,
) {
    Text(
        text = info,
        style = typographies.body,
    )
}

@Composable
private fun CurrentGameList(
    listCategory: CurrentGameListCategory,
    bottomSheetManager: BottomSheetManager,
) {
    Card(
        elevation = dimensions.cardElevation,
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        backgroundColor = themeColors.cardBackground,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = listCategory.gameListType.description,
                style = typographies.h1,
                modifier = Modifier.padding(dimensions.gap3),
            )
            PlayersList(
                viewState = PlayersListViewState(
                    players = listCategory.players,
                    showHierarchyLevel = listCategory.gameListType == CurrentGameListType.IN,
                    photoColorSaturation = if (listCategory.gameListType == CurrentGameListType.OUT) 0F else 1F
                ),
                photoSize = dimensions.playerPhotoCurrentList,
                onPlayerTapped = { player ->
                    bottomSheetManager.show {
                        PlayerDetail(PlayerDetailViewState(player = player))
                    }
                }
            )
        }
    }
}

@Composable
private fun PlayerListStatusButtons(
    state: GameListTabViewState.CurrentGameListViewState.Content,
    onInButtonClick: () -> Unit,
    onOutButtonClick:() -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.gap4)
    ) {
        state.outButtonViewState?.let {
            NegativeButton(
                viewState = it,
                onClick = onOutButtonClick,
                modifier = Modifier.weight(1f),
            )
        }

        state.inButtonViewState?.let {
            InButton(
                onClick = onInButtonClick,
                viewState = it,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun InButton(
    onClick: () -> Unit,
    viewState: ButtonViewState,
    modifier: Modifier = Modifier,
) {
    PrimaryButton(
        viewState = viewState,
        onClick = onClick,
        modifier = modifier,
    )
}
