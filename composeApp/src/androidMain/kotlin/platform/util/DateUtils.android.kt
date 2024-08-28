package platform.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

actual object DateUtils {

    actual fun formatMillisToFullDate(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val instant = Instant.ofEpochMilli(millis)
        val date = LocalDateTime.ofInstant(instant, ZoneId.of("CET"))

        return formatter.format(date)
    }

    actual fun formatMillisToMonthYear(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        val instant = Instant.ofEpochMilli(millis)
        val date = LocalDateTime.ofInstant(instant, ZoneId.of("CET"))

        return formatter.format(date)
    }
}
