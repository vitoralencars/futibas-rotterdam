package main.presentation

import profile.ui.model.BottomNavigationTabs

data class MainScreenState(
    val selectedTab: BottomNavigationTabs = BottomNavigationTabs.PROFILE,
    val visibleTabsIndexes: List<BottomNavigationTabs> = listOf(BottomNavigationTabs.PROFILE),
)
