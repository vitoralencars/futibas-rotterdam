import android.os.Build

actual fun getPlatform(): Platform = Platform.Android(
    name = "Android ${Build.VERSION.SDK_INT}",
)
