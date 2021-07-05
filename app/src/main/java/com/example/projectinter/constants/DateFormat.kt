package com.chipmong.dms.constants

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 24-Feb-20
 */
object DateFormat {
    const val TIME_FORMAT = "hh:mm aaa"
    const val DATE_FORMAT_3 = "dd MMM, yyyy hh:mm aaa"
    const val DATE_FORMAT_4 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    //Use for visitAt schedule
    const val DATE_FORMAT_5 = "yyyy-MM-dd"
    const val DATE_FORMAT_7 = "EEE dd MMM, yy"
    const val DEFAULT_DATE_FORMAT = "MMM dd, yyyy"
    const val HEADER_DATE_FORMAT = "MMM dd, yyyy"
    const val DEFAULT_DATE_FORMAT_TIME = "$DEFAULT_DATE_FORMAT | hh:mm a"


    fun getDTD(): ArrayList<String> {
        val dates = ArrayList<String>()
        dates.add(Calendar.getInstance().convertISOStartDate)
        dates.add(Calendar.getInstance().convertISOEndDate)
        return dates
    }

    fun getDYD(): ArrayList<String> {
        val startDate: Calendar
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        startDate = cal

        val dates = ArrayList<String>()
        dates.add(startDate.convertISOStartDate)
        dates.add(startDate.convertISOEndDate)
        return dates
    }

    fun getWTD(): ArrayList<String> {
        val startDate: Calendar
        val cal = Calendar.getInstance()

        startDate = if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            cal
        } else {
            cal.add(Calendar.DAY_OF_WEEK, -6)
            cal
        }

        val dates = ArrayList<String>()
        dates.add(startDate.convertISOStartDate)
        dates.add(Calendar.getInstance().convertISOEndDate)
        return dates
    }

    fun getMTD(): ArrayList<String> {
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.DAY_OF_MONTH, 1)

        val dates = ArrayList<String>()
        dates.add(startDate.convertISOStartDate)
        dates.add(Calendar.getInstance().convertISOEndDate)
        return dates
    }

    fun getYTD(): ArrayList<String> {
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.MONTH, Calendar.JANUARY)
        startDate.set(Calendar.DAY_OF_MONTH, 1)

        val dates = ArrayList<String>()
        dates.add(startDate.convertISOStartDate)
        dates.add(Calendar.getInstance().convertISOEndDate)
        return dates
    }

    val Calendar.convertISOStartDate: String
        get() {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            return this.time.getDateTimeForServer()
        }

    val Calendar.convertISOEndDate: String
        get() {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
            return this.time.getDateTimeForServer()
        }

    fun Date.getDateTimeForServer(): String {
        val df = SimpleDateFormat(DATE_FORMAT_4, Locale.US)
        df.timeZone = TimeZone.getTimeZone("UTC")
        return df.format(this)
    }
}