sealed interface Platform {
    val name: String

    data class Android(
        override val name: String
    ) : Platform

    data class IOS(
        override val name: String
    ) : Platform
}

expect fun getPlatform(): Platform
