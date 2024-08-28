@file:OptIn(ExperimentalMaterial3Api::class)

package registration.ui.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import common.ui.theme.dimensions
import components.datepicker.CustomDatePickerDialog
import components.form.ui.ClickableFormField
import components.form.ui.FormField
import components.form.viewstate.FormFieldViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.registration_identification_birthday
import futibasrotterdam.composeapp.generated.resources.registration_identification_favorite_team
import futibasrotterdam.composeapp.generated.resources.registration_identification_name
import futibasrotterdam.composeapp.generated.resources.registration_identification_nickname
import org.jetbrains.compose.resources.stringResource
import platform.util.DateUtils
import registration.presentation.model.IdentificationStepViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentificationStep(
    viewState: IdentificationStepViewState,
    onNameInputChange: (String) -> Unit,
    onNicknameInputChange: (String) -> Unit,
    onBirthdayFieldTapped: () -> Unit,
    onBirthdaySelected: (String?) -> Unit,
    onBirthdayDatePickerDismissed: () -> Unit,
    onFavoriteTeamInputChange: (String) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier.fillMaxSize(),
    ) {
        FormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.registration_identification_name),
                input = viewState.name
            ),
            onInputChange = onNameInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        FormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.registration_identification_nickname),
                input = viewState.nickname ?: "",
                isOptional = true,
            ),
            onInputChange = onNicknameInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        ClickableFormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.registration_identification_birthday),
                input = viewState.birthday ?: "",
                isOptional = true,
            ),
            onClick = { onBirthdayFieldTapped() },
        )

        FormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.registration_identification_favorite_team),
                input = viewState.favoriteTeam,
            ),
            onInputChange = onFavoriteTeamInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (viewState.shouldShowDatePickerDialog) {
            CustomDatePickerDialog(
                datePickerState = datePickerState,
                onDismiss = {
                    onBirthdayDatePickerDismissed()
                },
                onConfirm = {
                    datePickerState.selectedDateMillis?.let { selectedDate ->
                        onBirthdaySelected(DateUtils.formatMillisToFullDate(millis = selectedDate))
                    }
                },
                onDeleteData = {
                    onBirthdaySelected(null)
                }
            )
        }
    }

}
