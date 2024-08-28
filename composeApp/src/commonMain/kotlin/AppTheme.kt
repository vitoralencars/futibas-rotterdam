import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import common.ui.theme.Colors

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = Colors.schemeColors,
        content = content,
    )
}
