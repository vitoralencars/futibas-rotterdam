package platform.util

expect object DateUtils {

    fun formatMillisToFullDate(millis: Long): String

    fun formatMillisToMonthYear(millis: Long): String
}
