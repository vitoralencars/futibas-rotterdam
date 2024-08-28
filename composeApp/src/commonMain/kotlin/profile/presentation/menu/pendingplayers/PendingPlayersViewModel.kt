package profile.presentation.menu.pendingplayers

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.launch
import network.ServiceResult
import player.domain.model.Player
import player.domain.model.PlayerLevel
import player.domain.usecase.UpdatePlayerLevelUseCase
import profile.domain.usecase.FetchPendingPlayersUseCase

class PendingPlayersViewModel(
    private val fetchPendingPlayersUseCase: FetchPendingPlayersUseCase,
    private val updatePlayerLevelUseCase: UpdatePlayerLevelUseCase,
) : BaseViewModel<PendingPlayersScreenState, PendingPlayersSideEffect>(
    initialState = PendingPlayersScreenState.Loading()
) {

    init {
        fetchPendingPlayers()
    }

    private fun fetchPendingPlayers() {
        viewModelScope.launch {
            val players = when (val result = fetchPendingPlayersUseCase()) {
                is ServiceResult.Success -> result.response
                is ServiceResult.Error -> emptyList()
            }
            updateState {
                if (players.isNotEmpty()) {
                    PendingPlayersScreenState.Content(
                        playersViewStates = players.map {
                            PlayerItemViewState(
                                player = it.copy(
                                    level = PlayerLevel.SPOT,
                                )
                            )
                        }
                    )
                } else {
                    PendingPlayersScreenState.Empty()
                }.setRefreshing(false)
            }
        }
    }

    fun refreshPendingPlayers() {
        updateState {
            it.setRefreshing(isRefreshing = true)
        }
        fetchPendingPlayers()
    }

    fun onPlayerLevelSet(
        player: Player,
        playerLevel: PlayerLevel,
    ) {
        (state.value.asContent())?.let { screenState ->
            updateState {
                screenState.copy(
                    playersViewStates = screenState.playersViewStates.map {
                        if (it.player == player) {
                            it.copy(
                                player = player.copy(
                                    level = playerLevel,
                                )
                            )
                        } else it
                    }
                )
            }
        }
    }

    fun onPlayerApproved(playerId: String) {
        setActionLoadingState(
            playerId = playerId,
            actionStatusType = ActionStatus.Approving,
        )
        updatePlayerLevel(
            playerId = playerId,
            isApproved = true,
        )
    }

    fun onPlayerDenied(playerId: String) {
        setActionLoadingState(
            playerId = playerId,
            actionStatusType = ActionStatus.Denying,
        )
        updatePlayerLevel(
            playerId = playerId,
            isApproved = false,
        )
    }

    private fun setActionLoadingState(
        playerId: String,
        actionStatusType: ActionStatus,
    ) {
        (state.value.asContent())?.let { screenState ->
            updateState {
                screenState.copy(
                    playersViewStates = screenState.playersViewStates.map {
                        val actionStatus = if (playerId == it.player.playerId) {
                            actionStatusType
                        } else ActionStatus.None
                        it.copy(actionStatus = actionStatus)
                    }
                )
            }
        }
    }

    private fun updatePlayerLevel(
        playerId: String,
        isApproved: Boolean,
    ) {
        (state.value.asContent())?.let { screenState ->
            viewModelScope.launch {
                val result = if (isApproved) {
                    val newPlayerLevel = screenState.playersViewStates.find {
                        it.player.playerId == playerId
                    }?.player?.level

                    newPlayerLevel?.let {
                        updatePlayerLevelUseCase(
                            playerId = playerId,
                            newPlayerLevel = it,
                        )
                    }
                } else {
                    updatePlayerLevelUseCase(
                        playerId = playerId,
                        newPlayerLevel = PlayerLevel.REJECTED,
                    )
                }

                when (result) {
                    is ServiceResult.Success -> fetchPendingPlayers()
                    else -> {}
                }
            }
        }
    }
}
