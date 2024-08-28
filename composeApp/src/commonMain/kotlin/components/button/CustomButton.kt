package components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies

@Composable
private fun CustomButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    buttonColors: ButtonColors,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.NORMAL,
) {
    val isButtonEnabled = when (viewState) {
        is ButtonViewState.Content -> viewState.isEnabled
        is ButtonViewState.Loading -> false
    }

    val contentColor = if (buttonStyle == ButtonStyle.NORMAL) {
        buttonColors.contentColor
    } else{
        buttonColors.containerColor
    }
    val content: @Composable RowScope.() -> Unit = when (viewState) {
        is ButtonViewState.Content -> {{
            Text(
                text = viewState.text,
                style = typographies.h1Light,
                textAlign = TextAlign.Center,
                color = contentColor,
            )
        }}
        is ButtonViewState.Loading -> {{
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = dimensions.gap1,
                modifier = Modifier.size(dimensions.gap6)
            )
        }}
    }

    when (buttonStyle) {
        ButtonStyle.NORMAL -> Button(
            contentPadding = PaddingValues(dimensions.gap4),
            colors = buttonColors,
            enabled = isButtonEnabled,
            onClick = onClick,
            modifier = modifier,
            content = content,
        )
        ButtonStyle.OUTLINED -> OutlinedButton(
            contentPadding = PaddingValues(dimensions.gap4),
            colors = ButtonColors(
                contentColor = buttonColors.containerColor,
                containerColor = buttonColors.contentColor,
                disabledContentColor = buttonColors.disabledContainerColor,
                disabledContainerColor = buttonColors.disabledContentColor,
            ),
            border = BorderStroke(
                width = dimensions.borderThickness,
                color = buttonColors.containerColor,
            ),
            enabled = isButtonEnabled,
            onClick = onClick,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
fun PrimaryButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomButton(
        viewState = viewState,
        onClick = onClick,
        buttonColors = ButtonColors(
            contentColor = themeColors.secondaryColor,
            containerColor = themeColors.mainColor,
            disabledContentColor = themeColors.secondaryColor,
            disabledContainerColor = themeColors.mainColor.copy(alpha = .5f)
        ),
        modifier = modifier,
    )
}

@Composable
fun NegativeButton(
    viewState: ButtonViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.NORMAL,
) {
    CustomButton(
        viewState = viewState,
        onClick = onClick,
        buttonColors = ButtonColors(
            containerColor = themeColors.negativeContainerButtonColor,
            contentColor = themeColors.negativeContentButtonColor,
            disabledContentColor = themeColors.negativeContentButtonColor,
            disabledContainerColor = themeColors.negativeContainerButtonColor.copy(alpha = .5f),
        ),
        modifier = modifier,
        buttonStyle = buttonStyle,
    )
}

@Composable
fun DialogTextButton(
    viewState: ButtonViewState.Content,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = viewState.text,
        style = typographies.dialogTextButton,
        modifier = modifier.clickable { onClick() }
    )
}
