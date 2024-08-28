package components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import components.button.ButtonViewState
import components.button.DialogTextButton

@Composable
fun BinaryDialog(
    viewState: BinaryDialogViewState,
    onClick: (BinaryDialogClick) -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onClick(BinaryDialogClick.DENY) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
            modifier = Modifier
                .fillMaxWidth()
                .background(themeColors.secondaryColor)
                .padding(dimensions.gap4)
        ) {
            content()

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = dimensions.gap6,
                    alignment = Alignment.End,
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                DialogTextButton(
                    viewState = ButtonViewState.Content(
                        text = viewState.denyButtonText,
                    ),
                    onClick = { onClick(BinaryDialogClick.DENY) }
                )
                DialogTextButton(
                    viewState = ButtonViewState.Content(
                        text = viewState.confirmButtonText,
                    ),
                    onClick = { onClick(BinaryDialogClick.CONFIRM) }
                )
            }
        }
    }
}

