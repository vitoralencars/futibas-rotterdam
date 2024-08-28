package profile.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import common.ui.extension.shimmerEffect
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.bottomsheet.BottomSheetManager
import components.button.ButtonViewState
import components.button.PrimaryButton
import components.button.NegativeButton
import components.form.ui.ClickableFormField
import components.form.viewstate.FormFieldViewState
import components.players.detail.PlayerDetail
import components.players.detail.PlayerDetailViewState
import components.toolbar.Toolbar
import components.toolbar.ToolbarViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.pending_players_approve_button
import futibasrotterdam.composeapp.generated.resources.pending_players_empty_state
import futibasrotterdam.composeapp.generated.resources.pending_players_level_label
import futibasrotterdam.composeapp.generated.resources.pending_players_reject_button
import futibasrotterdam.composeapp.generated.resources.profile_menu_pending_players
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import player.domain.model.PlayerLevel
import profile.presentation.menu.pendingplayers.ActionStatus
import profile.presentation.menu.pendingplayers.PendingPlayersScreenState
import profile.presentation.menu.pendingplayers.PendingPlayersViewModel
import profile.presentation.menu.pendingplayers.PlayerItemViewState

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterialApi::class)
@Composable
fun PendingPlayersScreen(
    navController: NavController,
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel: PendingPlayersViewModel = koinViewModel<PendingPlayersViewModel>()

    val state = viewModel.state.collectAsState().value

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refreshPendingPlayers() }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = themeColors.defaultScreenBackground)
        ) {
            Toolbar(
                viewState = ToolbarViewState(stringResource(Res.string.profile_menu_pending_players)),
                navController = navController,
                shouldNavigateBack = true,
            )
            Column(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (state) {
                    is PendingPlayersScreenState.Loading -> LoadingView()
                    is PendingPlayersScreenState.Empty -> EmptyView(
                        modifier = Modifier.weight(1f)
                    )
                    is PendingPlayersScreenState.Content -> ContentView(
                        state = state,
                        viewModel = viewModel,
                        bottomSheetManager = bottomSheetManager,
                    )
                }
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
    state: PendingPlayersScreenState.Content,
    viewModel: PendingPlayersViewModel,
    bottomSheetManager: BottomSheetManager,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensions.gap4),
    ) {
        state.playersViewStates.forEach { playerItemViewState ->
            PlayerItem(
                viewState = playerItemViewState,
                bottomSheetManager = bottomSheetManager,
                onLevelSet = { level ->
                    viewModel.onPlayerLevelSet(
                        player = playerItemViewState.player,
                        playerLevel = level,
                    )
                },
                onPlayerApproved = {
                    viewModel.onPlayerApproved(playerId = playerItemViewState.player.playerId)
                },
                onPlayerDenied = {
                    viewModel.onPlayerDenied(playerId = playerItemViewState.player.playerId)
                },
            )
        }
    }
}

@Composable
private fun PlayerItem(
    viewState: PlayerItemViewState,
    bottomSheetManager: BottomSheetManager,
    onLevelSet: (level: PlayerLevel) -> Unit,
    onPlayerApproved: () -> Unit,
    onPlayerDenied: () -> Unit,
) {
    Card(
        elevation = dimensions.cardElevation,
        shape = RoundedCornerShape(dimensions.cardRoundedCorner),
        backgroundColor = themeColors.cardBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PlayerDetail(
                viewState = PlayerDetailViewState(
                    player = viewState.player,
                    shouldShowHierarchy = false,
                )
            )
            Spacer(modifier = Modifier.height(dimensions.gap4))
            ClickableFormField(
                viewState = FormFieldViewState(
                    label = stringResource(Res.string.pending_players_level_label),
                    input = viewState.player.level.description,
                ),
                onClick = {
                    bottomSheetManager.show {
                        PlayerLevel.entries.filter { playerLevel ->
                            playerLevel != PlayerLevel.PENDING &&
                            playerLevel != PlayerLevel.REJECTED
                        }.forEach { playerLevel ->
                            HierarchyItem(
                                playerLevel = playerLevel,
                                onLevelTapped = {
                                    bottomSheetManager.hide()
                                    onLevelSet(playerLevel)
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = dimensions.gap4),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(dimensions.gap4),
            ) {
                DenyButton(
                    actionStatus = viewState.actionStatus,
                    onClick = onPlayerDenied,
                )
                ApproveButton(
                    actionStatus = viewState.actionStatus,
                    onClick = onPlayerApproved,
                )
            }
        }
    }
}

@Composable
private fun HierarchyItem(
    playerLevel: PlayerLevel,
    onLevelTapped: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLevelTapped() }
    ) {
        Text(
            text = playerLevel.description,
            style = typographies.body,
            modifier = Modifier.fillMaxWidth().padding(dimensions.gap4)
        )
        Divider(
            color = themeColors.dividerColor,
            thickness = dimensions.dividerThickness,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DenyButton(
    actionStatus: ActionStatus,
    onClick: () -> Unit,
) {
    val buttonViewState = if (actionStatus is ActionStatus.Denying) {
        ButtonViewState.Loading
    } else {
        ButtonViewState.Content(
            text = stringResource(Res.string.pending_players_reject_button),
            isEnabled = actionStatus !is ActionStatus.Approving,
        )
    }
    NegativeButton(
        viewState = buttonViewState,
        onClick = onClick,
    )
}

@Composable
private fun ApproveButton(
    actionStatus: ActionStatus,
    onClick: () -> Unit,
) {
    val buttonViewState = if (actionStatus is ActionStatus.Approving) {
        ButtonViewState.Loading
    } else {
        ButtonViewState.Content(
            text = stringResource(Res.string.pending_players_approve_button),
            isEnabled = actionStatus !is ActionStatus.Denying,
        )
    }

    PrimaryButton(
        viewState = buttonViewState,
        onClick = onClick,
    )
}

@Composable
private fun LoadingView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier.padding(dimensions.gap4),
    ) {
        repeat(3) {
            LoadingItem()
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(dimensions.cardRoundedCorner))
            .shimmerEffect()
    )
}

@Composable
private fun EmptyView(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(Res.string.pending_players_empty_state),
            style = typographies.h1,
        )
    }
}
