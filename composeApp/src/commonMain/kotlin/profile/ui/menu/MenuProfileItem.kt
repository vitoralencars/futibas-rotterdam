package profile.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import profile.presentation.menu.MenuProfileItemViewState

@Composable
fun MenuProfileItem(
    viewState: MenuProfileItemViewState,
    onItemClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked() }
                .padding(vertical = dimensions.gap4)
        ) {
            Text(
                text = viewState.title,
                style = typographies.body,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = themeColors.mainColor,
            )
        }
        Divider(
            color = themeColors.mainColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
