package components.toolbar

import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import common.navigation.GlobalNavController
import common.ui.theme.themeColors
import common.ui.theme.typographies

@Composable
fun Toolbar(
    viewState: ToolbarViewState,
    navController: NavController? = null,
    shouldNavigateBack: Boolean = false,
) {
    TopAppBar(
        title = {
            Text(
                text = viewState.title,
                style = typographies.toolbar
            )
        },
        backgroundColor = themeColors.mainColor,
        navigationIcon = { if (shouldNavigateBack) NavigationIcon(navController) },
    )
}

@Composable
private fun NavigationIcon(navController: NavController?) {
    IconButton(
        onClick = {
            navController?.navigateUp() ?: run {
                GlobalNavController.navController.navigateUp()
            }
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = "",
            tint = themeColors.secondaryColor,
        )
    }
}
