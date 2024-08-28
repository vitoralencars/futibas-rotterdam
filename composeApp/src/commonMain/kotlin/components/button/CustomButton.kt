package components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.dialog.BinaryDialogClick

@Composable
fun CustomButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    buttonColors: ButtonColors? = null,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val isButtonEnabled = when (viewState) {
        is ButtonViewState.Content -> viewState.isEnabled
        is ButtonViewState.Loading -> false
    }

    Button(
        contentPadding = PaddingValues(dimensions.gap4),
        colors = buttonColors ?: ButtonColors(
            contentColor = themeColors.secondaryColor,
            containerColor = themeColors.mainColor,
            disabledContentColor = themeColors.secondaryColor,
            disabledContainerColor = themeColors.mainColor.copy(alpha = .5f)
        ),
        enabled = isButtonEnabled,
        onClick = onClick,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun TextButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    buttonColors: ButtonColors? = null,
    modifier: Modifier = Modifier,
) {
    CustomButton(
        viewState = viewState,
        onClick = onClick,
        buttonColors = buttonColors,
        modifier = modifier,
    ) {
        when (viewState) {
            is ButtonViewState.Content -> {
                Text(
                    text = viewState.text,
                    style = typographies.h1Light,
                    textAlign = TextAlign.Center,
                )
            }
            is ButtonViewState.Loading -> {
                CircularProgressIndicator(
                    color = buttonColors?.contentColor ?: themeColors.secondaryColor,
                    strokeWidth = dimensions.gap1,
                    modifier = Modifier.size(dimensions.gap6)
                )
            }
        }
    }
}

@Composable
fun MainButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomButton(
        viewState = viewState,
        onClick = onClick,
        modifier = modifier
    ) {
        when (viewState) {
            is ButtonViewState.Content -> {
                Text(
                    text = viewState.text,
                    style = typographies.h1Light,
                    textAlign = TextAlign.Center,
                )
            }
            is ButtonViewState.Loading -> {
                CircularProgressIndicator(
                    color = themeColors.secondaryColor,
                    strokeWidth = dimensions.gap1,
                    modifier = Modifier.size(dimensions.gap6)
                )
            }
        }
    }
}

@Composable
fun NegativeButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        viewState = viewState,
        onClick = onClick,
        buttonColors = ButtonColors(
            containerColor = themeColors.negativeContainerButtonColor,
            contentColor = themeColors.negativeContentButtonColor,
            disabledContentColor = themeColors.negativeContentButtonColor,
            disabledContainerColor = themeColors.negativeContainerButtonColor.copy(alpha = .5f),
        ),
        modifier = modifier,
    )
}

@Composable
fun DialogTextButton(
    viewState: ButtonViewState.Content,
    onClick: () -> Unit,
) {
    Text(
        text = viewState.text,
        style = typographies.dialogTextButton,
        modifier = Modifier.clickable { onClick() }
    )
}
