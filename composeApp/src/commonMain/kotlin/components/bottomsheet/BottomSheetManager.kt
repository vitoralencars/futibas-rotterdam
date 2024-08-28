@file:OptIn(ExperimentalMaterialApi::class)

package components.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BottomSheetManager(
    private val scope: CoroutineScope,
    val sheetState: ModalBottomSheetState,
    val contentState: MutableState<(@Composable ColumnScope.() -> Unit)?>
) {
    fun show(content: @Composable ColumnScope.() -> Unit) {
        scope.launch {
            contentState.value = content
            sheetState.show()
        }
    }

    fun hide() {
        scope.launch {
            sheetState.hide()
            contentState.value = null
        }
    }
}

@Composable
fun rememberBottomSheetManager(showExpanded: Boolean = false): BottomSheetManager {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = showExpanded,
    )
    val scope = rememberCoroutineScope()
    val contentState = remember { mutableStateOf<(@Composable ColumnScope.() -> Unit)?>(null) }
    return remember { BottomSheetManager(scope, sheetState, contentState) }
}
