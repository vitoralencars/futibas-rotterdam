package common.navigation

import common.util.isPrimitiveOrString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class ScreenNavigation(val route: String) {
    data object Splash: ScreenNavigation(route = "Splash")
    data object Login: ScreenNavigation(route = "Login")
    data object Registration: ScreenNavigation(route = "Registration/{${NavArguments.PLAYER_ID}}/{${NavArguments.PLAYER_NAME}}/{${NavArguments.PLAYER_EMAIL}}")
    data object Main: ScreenNavigation(route = "Main")

    inline fun <reified T>  ScreenNavigation.createRouteWithArguments(
        arguments: Map<String, T>,
    ): String {
        var finalRoute = route

        arguments.entries.forEach {
            val value = it.value
            val finalValue = if (value.isPrimitiveOrString()) {
                val valueString = value.toString()
                if (valueString.isBlank()) "<empty>" else value.toString()
            } else {
                Json.encodeToString(it.value)
            }

            finalRoute = finalRoute.replace("{${it.key}}", finalValue)
        }

        return finalRoute
    }
}
