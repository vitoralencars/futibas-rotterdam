@file:OptIn(ExperimentalMaterialApi::class)

package main.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.bottomsheet.BottomSheetDragHandler
import components.bottomsheet.BottomSheetManager
import components.bottomsheet.rememberBottomSheetManager
import common.ui.theme.Colors
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.clipboard_list
import futibasrotterdam.composeapp.generated.resources.football_player
import futibasrotterdam.composeapp.generated.resources.list_tab
import futibasrotterdam.composeapp.generated.resources.players_tab
import futibasrotterdam.composeapp.generated.resources.profile
import futibasrotterdam.composeapp.generated.resources.profile_tab
import gamelist.ui.GameListScreen
import main.presentation.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.permission.PermissionCallback
import platform.permission.PermissionManager
import platform.permission.PermissionStatus
import platform.permission.PermissionType
import platform.permission.createPermissionManager
import playerslist.ui.PlayersScreen
import profile.ui.ProfileScreen
import profile.ui.model.BottomNavigationTabs

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = koinViewModel<MainViewModel>()

    val bottomSheetManager = rememberBottomSheetManager()
    val bottomSheetContent by bottomSheetManager.contentState

    val state = viewModel.state.collectAsState().value

    requestPushNotificationsPermission(createPermissionManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            if (status == PermissionStatus.GRANTED) {
                viewModel.onPushNotificationPermissionGranted()
            }
        }
    }))

    ModalBottomSheetLayout(
        sheetState = bottomSheetManager.sheetState,
        sheetShape = RoundedCornerShape(
            topStart = dimensions.bottomSheetRoundedCorner,
            topEnd = dimensions.bottomSheetRoundedCorner
        ),
        sheetContent = {
            bottomSheetContent?.let { content ->
                BottomSheetDragHandler()
                content()
            }
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = themeColors.mainColor,
                ){
                    getTabsTitles(state.visibleTabsIndexes).forEachIndexed { index, tab ->
                        val isSelected = state.selectedTab == state.visibleTabsIndexes[index]
                        NavigationBarItem(
                            icon = { Icon(
                                painter = getTabIcon(
                                    tabs = state.visibleTabsIndexes,
                                    index = index,
                                ),
                                tint = if (isSelected) themeColors.mainColor else Colors.white,
                                contentDescription = null,
                            ) },
                            label = {
                                Text(
                                    text = tab,
                                    style = typographies.body.copy(color = Colors.white),
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                viewModel.onTabClicked(selectedTabIndex = index)
                            },
                        )
                    }
                }
            }
        ) { innerPadding ->
            MainContent(
                innerPadding = innerPadding,
                selectedTab = state.selectedTab,
                bottomSheetManager = bottomSheetManager,
            )
        }
    }

}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    selectedTab: BottomNavigationTabs,
    bottomSheetManager: BottomSheetManager,
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        when (selectedTab) {
            BottomNavigationTabs.PLAYERS_LIST -> PlayersScreen(
                bottomSheetManager = bottomSheetManager,
            )
            BottomNavigationTabs.GAME_LIST -> GameListScreen(
                bottomSheetManager = bottomSheetManager,
            )
            BottomNavigationTabs.PROFILE -> ProfileScreen(
                bottomSheetManager = bottomSheetManager,
            )
        }
    }
}

@Composable
private fun getTabIcon(
    tabs: List<BottomNavigationTabs>,
    index: Int,
) = when (tabs[index]) {
    BottomNavigationTabs.PLAYERS_LIST -> painterResource(Res.drawable.football_player)
    BottomNavigationTabs.GAME_LIST -> painterResource(Res.drawable.clipboard_list)
    BottomNavigationTabs.PROFILE -> painterResource(Res.drawable.profile)
}

@Composable
private fun getTabsTitles(visibleTabs: List<BottomNavigationTabs>): List<String> {
    val tabsTitles = arrayListOf<String>()

    visibleTabs.forEach { tab ->
        when (tab) {
            BottomNavigationTabs.PLAYERS_LIST -> tabsTitles.add(stringResource(Res.string.players_tab))
            BottomNavigationTabs.GAME_LIST -> tabsTitles.add(stringResource(Res.string.list_tab))
            BottomNavigationTabs.PROFILE -> tabsTitles.add(stringResource(Res.string.profile_tab))
        }
    }

    return tabsTitles
}

@Composable
private fun requestPushNotificationsPermission(permissionManager: PermissionManager) {
    val permission = PermissionType.PUSH_NOTIFICATIONS
    if (!permissionManager.isPermissionGranted(permission)) {
        permissionManager.askPermission(permission)
    }
}
