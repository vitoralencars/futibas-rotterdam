package platform.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.systemTimeZone
import platform.Foundation.timeZoneWithName

actual object DateUtils {

    actual fun formatMillisToFullDate(millis: Long): String {
        val date = NSDate.dateWithTimeIntervalSince1970(millis / 1000.0)
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = "dd/MM/yyyy"
            timeZone = NSTimeZone.timeZoneWithName("CET") ?: NSTimeZone.systemTimeZone
            locale = NSLocale.currentLocale
        }
        return dateFormatter.stringFromDate(date)
    }

    actual fun formatMillisToMonthYear(millis: Long): String {
        val date = NSDate.dateWithTimeIntervalSince1970(millis / 1000.0)
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = "MMMM yyyy"
            timeZone = NSTimeZone.timeZoneWithName("CET") ?: NSTimeZone.systemTimeZone
            locale = NSLocale.currentLocale
        }
        return dateFormatter.stringFromDate(date)
    }
}
