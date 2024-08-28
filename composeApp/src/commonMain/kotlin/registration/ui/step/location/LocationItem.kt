package registration.ui.step.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.form.viewstate.LocationFormItemViewState

@Composable
fun LocationItem(
    viewState: LocationFormItemViewState,
    onItemSelected: (LocationFormItemViewState) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemSelected(viewState)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.gap4)
        ) {
            viewState.flag?.let { flag ->
                Text(text = flag)
                Spacer(Modifier.width(dimensions.gap4))
            }
            Text(
                text = viewState.placeName,
                style = typographies.body,
            )
        }

        Divider(
            color = themeColors.dividerColor,
            thickness = dimensions.dividerThickness,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
