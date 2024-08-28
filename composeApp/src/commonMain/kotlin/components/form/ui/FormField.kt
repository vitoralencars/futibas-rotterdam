package components.form.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.bottomsheet.BottomSheetManager
import components.form.viewstate.FormFieldViewState
import components.form.viewstate.LocationFormItemViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.form_field_optional_label
import futibasrotterdam.composeapp.generated.resources.form_field_select_item
import org.jetbrains.compose.resources.stringResource
import registration.ui.step.location.LocationItem

@Composable
fun FormField(
    viewState: FormFieldViewState,
    onInputChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = viewState.label,
                style = typographies.h2,
            )

            if (viewState.isOptional) {
                Spacer(modifier = Modifier.width(dimensions.gap2))
                Text(
                    text = stringResource(Res.string.form_field_optional_label),
                    style = typographies.bodyLight,
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensions.gap05))
        BasicTextField(
            value = viewState.input,
            onValueChange = onInputChange,
            textStyle = typographies.body,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
            }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .border(
                            width = dimensions.borderThickness,
                            color = themeColors.formBorderColor,
                            shape = RoundedCornerShape(dimensions.gap1),
                        )
                        .background(
                            color = themeColors.secondaryColor,
                            shape = RoundedCornerShape(dimensions.gap1),
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensions.gap2,
                            vertical = dimensions.gap3
                        )
                ) {
                    innerTextField()
                }
            },
            modifier = modifier,
        )
    }
}

@Composable
fun ClickableFormField(
    viewState: FormFieldViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = viewState.label,
                style = typographies.h2,
            )

            if (viewState.isOptional) {
                Spacer(modifier = Modifier.width(dimensions.gap2))
                Text(
                    text = stringResource(Res.string.form_field_optional_label),
                    style = typographies.bodyLight,
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensions.gap05))

        Box(
            modifier = Modifier
                .border(
                    width = dimensions.borderThickness,
                    color = themeColors.formBorderColor,
                    shape = RoundedCornerShape(dimensions.gap1),
                )
                .background(
                    color = themeColors.secondaryColor,
                    shape = RoundedCornerShape(dimensions.gap1),
                )
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(
                    horizontal = dimensions.gap2,
                    vertical = dimensions.gap3,
                )
        ) {
            Text(
                text = viewState.input,
                style = typographies.body,
            )
        }
    }
}

@Composable
fun LocationSelectionForm(
    formFieldViewState: FormFieldViewState,
    locationFormItemViewStates: List<LocationFormItemViewState>,
    bottomSheetManager: BottomSheetManager,
    onItemSelected: (LocationFormItemViewState) -> Unit,
) {
    ClickableFormField(
        viewState = formFieldViewState,
        onClick = {
            bottomSheetManager.show {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(
                            Res.string.form_field_select_item,
                            formFieldViewState.label.toLowerCase(Locale.current),
                        ),
                        style = typographies.h1,
                        modifier = Modifier.padding(dimensions.gap4)
                    )
                    LazyColumn {
                        items(locationFormItemViewStates) { viewState ->
                            LocationItem(
                                viewState = LocationFormItemViewState(
                                    placeName = viewState.placeName,
                                    flag = viewState.flag,
                                ),
                                onItemSelected = { locationItemViewState ->
                                    onItemSelected(locationItemViewState)
                                    bottomSheetManager.hide()
                                }
                            )
                        }
                    }
                }
            }
        },
    )
}
