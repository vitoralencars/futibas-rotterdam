package platform.image

class ImageSourceManager(
    private val onLaunch: () -> Unit
) {
    fun launch() {
        onLaunch()
    }
}
