package platform.util

import android.content.Intent
import android.net.Uri
import com.rotterdam.futibas.FutibasApplication

actual fun openUrl(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    FutibasApplication.CONTEXT.startActivity(intent)
}
