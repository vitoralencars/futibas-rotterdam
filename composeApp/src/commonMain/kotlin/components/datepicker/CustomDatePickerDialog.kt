@file:OptIn(ExperimentalMaterial3Api::class)

package components.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.button.ButtonViewState
import components.button.DialogTextButton
import platform.DateUtils

@Composable
fun CustomDatePickerDialog(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = @Composable {
            Button(
                contentPadding = PaddingValues(dimensions.gap4),
                colors = ButtonColors(
                    contentColor = themeColors.secondaryColor,
                    containerColor = themeColors.mainColor,
                    disabledContentColor = themeColors.secondaryColor,
                    disabledContainerColor = themeColors.mainColor.copy(alpha = .5f)
                ),
                onClick = onConfirm,
            ) {
                Text(
                    text = "Confirmar",
                    style = typographies.datePickerButton,
                    textAlign = TextAlign.Center,
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            title = @Composable {},
            dateFormatter = CustomDatePickerFormatter(),
        )
    }
}

@Composable
fun CustomTimePickerDialog(
    timePickerState: TimePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = themeColors.secondaryColor,
                    shape = RoundedCornerShape(dimensions.gap4),
                )
                .padding(dimensions.gap4),
        ) {
            TimePicker(
                state = timePickerState,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = dimensions.gap6,
                    alignment = Alignment.End,
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                DialogTextButton(
                    viewState = ButtonViewState.Content(
                        text = "Cancelar",
                    ),
                    onClick = onDismiss
                )
                DialogTextButton(
                    viewState = ButtonViewState.Content(
                        text = "Confirmar",
                    ),
                    onClick = onConfirm
                )
            }
        }
    }
}

private class CustomDatePickerFormatter : DatePickerFormatter {
    override fun formatMonthYear(
        monthMillis: Long?,
        locale: CalendarLocale
    ): String? {

        if (monthMillis == null) return null
        return DateUtils.formatMillisToMonthYear(monthMillis)
    }

    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String? {
        if (dateMillis == null) return null
        return DateUtils.formatMillisToFullDate(dateMillis)
    }
}
