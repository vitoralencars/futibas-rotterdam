@file:OptIn(ExperimentalMaterial3Api::class)

package components.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import components.button.ButtonViewState
import components.button.DialogTextButton
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.cancel_button
import futibasrotterdam.composeapp.generated.resources.confirm_button
import futibasrotterdam.composeapp.generated.resources.delete_button
import org.jetbrains.compose.resources.stringResource
import platform.util.DateUtils

@Composable
fun CustomDatePickerDialog(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onDeleteData: (() -> Unit)? = null,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            DialogTextButton(
                viewState = ButtonViewState.Content(
                    text = stringResource(Res.string.confirm_button),
                ),
                onClick = onConfirm,
                modifier = Modifier.padding(
                    bottom = dimensions.gap4,
                    end = dimensions.gap4,
                ),
            )
        },
        dismissButton = onDeleteData?.let{{
            DialogTextButton(
                viewState = ButtonViewState.Content(
                    text = stringResource(Res.string.delete_button),
                ),
                onClick = onDeleteData,
                modifier = Modifier.padding(
                    bottom = dimensions.gap4,
                    end = dimensions.gap4,
                ),
            )
        }}
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
                        text = stringResource(Res.string.cancel_button),
                    ),
                    onClick = onDismiss
                )
                DialogTextButton(
                    viewState = ButtonViewState.Content(
                        text = stringResource(Res.string.confirm_button),
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
