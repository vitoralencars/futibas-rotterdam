package components.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import common.ui.theme.dimensions
import common.ui.theme.themeColors

@Composable
fun BottomSheetDragHandler() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.gap4)
    ) {
        Divider(
            modifier = Modifier
                .width(dimensions.bottomSheetDragHandlerWidth)
                .clip(CircleShape),
            color = themeColors.bottomSheetDragHandlerColor,
            thickness = dimensions.bottomSheetDragHandlerThickness
        )
    }
}
