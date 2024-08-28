package platform.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.util.KoinHelper.context

actual fun openUrl(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

private object KoinHelper : KoinComponent {
    val context: Context by inject()
}
