package common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface Dimensions {
    // Gaps
    val gap0: Dp
    val gap05: Dp
    val gap1: Dp
    val gap2: Dp
    val gap3: Dp
    val gap4: Dp
    val gap5: Dp
    val gap6: Dp
    val gap7: Dp
    val gap8: Dp
    val gap9: Dp
    val gap10: Dp

    // Toolbar
    val toolbarHeight: Dp

    // Bottom Sheet
    val bottomSheetRoundedCorner: Dp
    val bottomSheetDragHandlerWidth: Dp
    val bottomSheetDragHandlerThickness: Dp

    // Divider
    val dividerThickness: Dp

    // Border
    val borderThickness: Dp

    // Stepper
    val stepViewSize: Dp
    val minStepViewWidth: Dp

    // Card
    val cardElevation: Dp
    val cardRoundedCorner: Dp

    // Player
    val playerPhotoList: Dp

    // Tab row
    val tabRowRoundedCorner: Dp

    // Game List
    val playerPhotoCurrentList: Dp
    val playerPhotoHistoryList: Dp

    // Profile
    val playerPhotoProfile: Dp
    val profileMenuRoundedCorner: Dp
}

val dimensionsInstance = object : Dimensions {
    // Gaps
    override val gap0: Dp = 0.dp
    override val gap05: Dp = 2.dp
    override val gap1: Dp = 4.dp
    override val gap2: Dp = 8.dp
    override val gap3: Dp = 12.dp
    override val gap4: Dp = 16.dp
    override val gap5: Dp = 20.dp
    override val gap6: Dp = 24.dp
    override val gap7: Dp = 28.dp
    override val gap8: Dp = 32.dp
    override val gap9: Dp = 36.dp
    override val gap10: Dp = 40.dp

    // Toolbar
    override val toolbarHeight: Dp = 56.dp

    // Bottom Sheet
    override val bottomSheetRoundedCorner: Dp = 16.dp
    override val bottomSheetDragHandlerWidth: Dp = 60.dp
    override val bottomSheetDragHandlerThickness: Dp = 4.dp

    // Divider
    override val dividerThickness: Dp = 1.dp

    // Border
    override val borderThickness: Dp = 1.dp

    // Stepper
    override val stepViewSize: Dp = 24.dp
    override val minStepViewWidth: Dp = 60.dp

    // Card
    override val cardElevation: Dp = 5.dp
    override val cardRoundedCorner: Dp = 12.dp

    // Player
    override val playerPhotoList: Dp = 60.dp

    // Tab row
    override val tabRowRoundedCorner: Dp = 12.dp

    // Game List
    override val playerPhotoCurrentList: Dp = 48.dp
    override val playerPhotoHistoryList: Dp = 32.dp

    // Profile
    override val playerPhotoProfile: Dp = 120.dp
    override val profileMenuRoundedCorner: Dp = 24.dp
}

val LocalDimensions = compositionLocalOf { dimensionsInstance }

val dimensions: Dimensions
    @Composable @ReadOnlyComposable
    get() = LocalDimensions.current
