package platform.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.registerForRemoteNotifications
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatus
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusProvisional
import platform.UserNotifications.UNUserNotificationCenter

actual class PermissionManager actual constructor(
    private val callback: PermissionCallback,
) : PermissionHandler {

    @Composable
    actual override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                askCameraPermission(status, callback)
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                askGalleryPermission(status, callback)
            }

            PermissionType.PUSH_NOTIFICATIONS -> {
                val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
                notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
                    settings?.let {
                        askPushNotificationsPermission(
                            it.authorizationStatus,
                            callback,
                        )
                    }
                }
            }
        }
    }

    @Composable
    actual override fun isPermissionGranted(permission: PermissionType) =
        when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                status == AVAuthorizationStatusAuthorized
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                status == PHAuthorizationStatusAuthorized
            }

            PermissionType.PUSH_NOTIFICATIONS -> {
                var isGranted = false
                val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
                notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
                    isGranted = settings?.let {
                        when (it.authorizationStatus) {
                            UNAuthorizationStatusAuthorized -> true
                            UNAuthorizationStatusProvisional -> true
                            else -> false
                        }
                    } ?: false
                }
                isGranted
            }
        }

    @Composable
    actual override fun launchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }

    private fun askCameraPermission(
        status: AVAuthorizationStatus,
        callback: PermissionCallback,
    ) {
        val permission = PermissionType.CAMERA
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }
            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { isGranted ->
                    if (isGranted) {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }
            }
            AVAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }
            else -> error("unknown camera status $status")
        }
    }

    private fun askGalleryPermission(
        status: PHAuthorizationStatus,
        callback: PermissionCallback,
    ) {
        val permission = PermissionType.GALLERY
        when (status) {
            PHAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }
            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                    askGalleryPermission(newStatus, callback)
                }
            }
            PHAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }
            else -> error("unknown gallery status $status")
        }
    }

    private fun askPushNotificationsPermission(
        status: UNAuthorizationStatus,
        callback: PermissionCallback,
    ) {
        val permission = PermissionType.GALLERY
        when (status) {
            UNAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }
            UNAuthorizationStatusNotDetermined -> {
                val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
                notificationCenter.requestAuthorizationWithOptions(
                    options = UNAuthorizationOptionAlert or
                            UNAuthorizationOptionSound or
                            UNAuthorizationOptionBadge
                ) { isGranted, _ ->
                    if (isGranted) {
                        UIApplication.sharedApplication.registerForRemoteNotifications()
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }

            }
            UNAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }
            else -> error("unknown push notifications status $status")
        }
    }
}

@Composable
actual fun createPermissionManager(callback: PermissionCallback): PermissionManager {
    return PermissionManager(callback)
}
