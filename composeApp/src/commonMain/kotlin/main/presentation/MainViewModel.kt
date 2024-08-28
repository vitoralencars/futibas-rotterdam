package main.presentation

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.launch
import platform.pushnotifications.subscribeToFCMTopic
import player.domain.mapper.toPLayerLevel
import player.domain.model.PlayerLevel
import player.domain.usecase.RetrieveLoggedInPlayerUseCase
import profile.ui.model.BottomNavigationTabs

class MainViewModel(
    private val retrieveLoggedInPlayerUseCase: RetrieveLoggedInPlayerUseCase,
) : BaseViewModel<MainScreenState, Nothing>(
    initialState = MainScreenState()
) {

    init {
        setVisibleTabs()
    }

    private fun setVisibleTabs() {
        viewModelScope.launch {
            val loggedInPlayer = retrieveLoggedInPlayerUseCase()
            loggedInPlayer?.let {  player ->
                if (player.level.toPLayerLevel() !in listOf(
                    PlayerLevel.PENDING,
                    PlayerLevel.REJECTED,
                )) {
                    updateState {
                        it.copy(
                            selectedTab = BottomNavigationTabs.PLAYERS_LIST,
                            visibleTabsIndexes = listOf(
                                BottomNavigationTabs.PLAYERS_LIST,
                                BottomNavigationTabs.GAME_LIST,
                                BottomNavigationTabs.PROFILE,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onTabClicked(selectedTabIndex: Int) {
        updateState {
            it.copy(selectedTab = it.visibleTabsIndexes[selectedTabIndex])
        }
    }

    fun onPushNotificationPermissionGranted() {
        subscribeToFCMTopic(topic = "allDevices")
    }
}
