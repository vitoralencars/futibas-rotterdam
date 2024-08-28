package common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

interface ThemeColors {
    // Typographies
    val typographyHeadingColor: Color
    val typographyBodyColor: Color
    val typographyBodyLightColor: Color
    val typographyToolbarColor: Color
    val typographyNavBarColor: Color
    val typographyViewedStepperColor: Color
    val typographyFutureStepperColor: Color
    val typographyDatePickerButtonColor: Color

    // Theme
    val mainColor: Color
    val secondaryColor: Color
    val defaultScreenBackground: Color
    val lightBackground: Color

    // Button
    val googleLoginButtonBackground: Color
    val appleLoginButtonBackground: Color
    val negativeContainerButtonColor: Color
    val negativeContentButtonColor: Color

    // Shimmer effect
    val shimmerEffectStartColor: Color
    val shimmerEffectMiddleColor: Color
    val shimmerEffectEndColor: Color

    // Bottom Sheet
    val bottomSheetDragHandlerColor: Color

    // Divider
    val dividerColor: Color

    // Card
    val cardBackground: Color

    // Form
    val formBorderColor: Color

    // Image source icon
    val imageSourceIconColor: Color
}

val themeColorsInstance = object : ThemeColors {
    // Typographies
    override val typographyHeadingColor = Colors.black
    override val typographyBodyColor = Colors.black
    override val typographyBodyLightColor = Colors.lightGrey
    override val typographyToolbarColor = Colors.white
    override val typographyNavBarColor = Colors.futibasGreen
    override val typographyViewedStepperColor = Colors.black
    override val typographyFutureStepperColor = Colors.lightGrey
    override val typographyDatePickerButtonColor = Colors.futibasGreen

    // Theme
    override val mainColor = Colors.futibasGreen
    override val secondaryColor = Colors.white
    override val defaultScreenBackground = Colors.futibasGreen.copy(alpha = .2f)
    override val lightBackground = Colors.white

    // Button
    override val googleLoginButtonBackground = Colors.white
    override val appleLoginButtonBackground = Colors.black
    override val negativeContainerButtonColor = Colors.red
    override val negativeContentButtonColor = Colors.white

    // Shimmer effect
    override val shimmerEffectStartColor = Colors.veryLightGrey
    override val shimmerEffectMiddleColor = Colors.white
    override val shimmerEffectEndColor = Colors.veryLightGrey

    // Bottom Sheet
    override val bottomSheetDragHandlerColor = Colors.lightGrey

    // Divider
    override val dividerColor = Colors.veryLightGrey

    // Card
    override val cardBackground = Colors.white

    // Form
    override val formBorderColor = Colors.veryLightGrey

    // Image source icon
    override val imageSourceIconColor = Colors.lightGrey
}

val LocalThemeColors = compositionLocalOf { themeColorsInstance }

val themeColors: ThemeColors
    @Composable @ReadOnlyComposable
    get() = LocalThemeColors.current
