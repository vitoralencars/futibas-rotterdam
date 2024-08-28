package profile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import components.bottomsheet.BottomSheetManager
import profile.ui.navigation.ProfileNavGraph

@Composable
fun ProfileScreen(
    bottomSheetManager: BottomSheetManager,
) {
    val profileNavController = rememberNavController()

    ProfileNavGraph(
        navHostController = profileNavController,
        bottomSheetManager = bottomSheetManager,
    )
}
