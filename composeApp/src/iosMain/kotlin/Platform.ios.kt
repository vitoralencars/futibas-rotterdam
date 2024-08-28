import platform.UIKit.UIDevice

actual fun getPlatform(): Platform = Platform.IOS(
    name = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion,
)
