package common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

interface Typographies {
    val h1: TextStyle
        @Composable get
    val h1Light: TextStyle
        @Composable get
    val h2: TextStyle
        @Composable get
    val h2Light: TextStyle
        @Composable get
    val h3: TextStyle
        @Composable get
    val h4: TextStyle
        @Composable get
    val h5: TextStyle
        @Composable get
    val body: TextStyle
        @Composable get
    val bodyPlus: TextStyle
        @Composable get
    val bodyBold: TextStyle
        @Composable get
    val bodyLight: TextStyle
        @Composable get
    val bodySecondary: TextStyle
        @Composable get
    val toolbar: TextStyle
        @Composable get
    val navBar: TextStyle
        @Composable get
    val tabSelected: TextStyle
        @Composable get
    val tabNeutral: TextStyle
        @Composable get
    val stepperViewed: TextStyle
        @Composable get
    val stepperFuture: TextStyle
        @Composable get
    val datePickerButton: TextStyle
        @Composable get
    val dialogTextButton: TextStyle
        @Composable get
}

val typographiesInstance = object : Typographies {
    override val h1: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyHeadingColor
        )
    override val h1Light: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.secondaryColor
        )
    override val h2: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyHeadingColor
        )
    override val h2Light: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.secondaryColor
        )
    override val h3: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyHeadingColor
        )
    override val h4: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyHeadingColor
        )
    override val h5: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyHeadingColor
        )
    override val body: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = themeColors.typographyBodyColor
        )
    override val bodyPlus: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = themeColors.typographyBodyColor
        )
    override val bodyBold: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyBodyColor
        )
    override val bodyLight: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = themeColors.typographyBodyLightColor
        )
    override val bodySecondary: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = themeColors.secondaryColor
        )
    override val toolbar: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = themeColors.typographyToolbarColor
        )
    override val navBar: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = themeColors.dividerColor
        )
    override val tabSelected: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = themeColors.secondaryColor
        )
    override val tabNeutral: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = themeColors.mainColor
        )
    override val stepperViewed: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyViewedStepperColor
        )
    override val stepperFuture: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.typographyFutureStepperColor
        )
    override val datePickerButton: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.secondaryColor
        )
    override val dialogTextButton: TextStyle
        @Composable get() = TextStyle(
            fontFamily = OpenSansFontFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.mainColor
        )
}

val LocalTypographies = compositionLocalOf { typographiesInstance }

val typographies: Typographies
    @Composable @ReadOnlyComposable
    get() = LocalTypographies.current
