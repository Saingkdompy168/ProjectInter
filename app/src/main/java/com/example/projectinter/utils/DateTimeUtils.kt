package com.chipmong.dms.utils

import android.text.format.DateUtils
import com.chipmong.dms.constants.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
object DateTimeUtils {

    fun convertTomorrowDate(format: String): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_YEAR] = calendar[Calendar.DAY_OF_YEAR] + 1
        return convertDateFromMillis(calendar.timeInMillis, format)
    }

    fun convertWeekToDate(format: String): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_YEAR] = calendar[Calendar.DAY_OF_YEAR] + 4
        return convertDateFromMillis(calendar.timeInMillis, format)
    }

    fun convertToDay(format: String): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_YEAR] = calendar[Calendar.DAY_OF_YEAR]
        return convertDateFromMillis(calendar.timeInMillis, format)
    }

    @Throws(Exception::class)
    fun convertDateFromMillis(timeInMill: Long, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(timeInMill))
    }

    @Throws(Exception::class)
    fun convertTimeFromMillis(timeInMill: Long, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return Date(timeInMill).time
    }

    @Throws(Exception::class)
    fun convertTimeFromString(timeInMill: String, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        val date = sdf.parse(timeInMill)
        return date.time
    }

    @Throws(Exception::class)
    fun convertDateFromMillisToUTC(timeInMill: Long, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date(timeInMill))
    }

    fun getCurrentTime(time: Long): String {
        return convertDateFromMillis(time, DateFormat.TIME_FORMAT)
    }


    fun getTimeFromServer(time: String): String {
        return convertDateFromServer(time, DateFormat.TIME_FORMAT)
    }

    @Throws(Exception::class)
    fun convertDateFromServer(time: String, format: String): String {
        val sdf = SimpleDateFormat(DateFormat.DATE_FORMAT_4, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(time)
        return convertDateFromMillis(date.time, format)
    }

    @Throws(Exception::class)
    fun convertTimeFromServer(time: String, format: String): Long {
        val sdf = SimpleDateFormat(DateFormat.DATE_FORMAT_4, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(time)
        return convertTimeFromMillis(date.time, format)
    }

    @Throws(Exception::class)
    fun convertTimeFromServer(time: String): Long {
        val sdfUTC = SimpleDateFormat(DateFormat.DATE_FORMAT_4, Locale.getDefault())
        sdfUTC.timeZone = TimeZone.getTimeZone("UTC")
        val dateUtc = sdfUTC.parse(time)

        val sdf = SimpleDateFormat(DateFormat.DATE_FORMAT_3, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()


        val dateString = convertDateFromMillis(dateUtc.time, DateFormat.DATE_FORMAT_3)
        val date = sdf.parse(dateString)
        return date.time
    }

    fun convertDate(time: String, format: String, outFormat: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(time)
        return convertDateFromMillis(date.time, outFormat)
    }

    //Convert UFC to current
    @Throws(Exception::class)
    fun getDateFromServer(time: String): Date? {
        val sdf = SimpleDateFormat(DateFormat.DATE_FORMAT_4, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.parse(time)
    }

    /**
     * UTC format date
     */
    @Throws(Exception::class)
    fun getDayOfWeekFromServer(time: String): Int {
        val sdf = SimpleDateFormat(DateFormat.DATE_FORMAT_4, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(time)
        return if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar[Calendar.DAY_OF_WEEK]
        } else {
            0
        }
    }

    /**
     * Date format yyyy-MM-dd
     */
    @Throws(Exception::class)
    fun getDayOfWeek(date: String, format: String): Int {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        val _date = sdf.parse(date)
        return if (_date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = _date
            calendar[Calendar.DAY_OF_WEEK]
        } else {
            0
        }
    }


    fun convertCurrentDateToUTC(): String {
        return convertDateFromMillisToUTC(System.currentTimeMillis(), DateFormat.DATE_FORMAT_4)
    }

    fun convertTomorrowToUTC(): String {
        return convertDateFromMillis(getTomorrowMill(), DateFormat.DATE_FORMAT_4)
    }

    private fun getTomorrowMill(): Long {
        val cal = Calendar.getInstance() //current date and time
        cal.add(Calendar.DAY_OF_MONTH, 1) //add a day
        cal.set(Calendar.HOUR_OF_DAY, 23) //set hour to last hour
        cal.set(Calendar.MINUTE, 59) //set minutes to last minute
        cal.set(Calendar.SECOND, 59) //set seconds to last second
        cal.set(Calendar.MILLISECOND, 999) //set milliseconds to last millisecond
        return cal.timeInMillis
    }

    fun isTodayFromServer(time: String): Boolean {
        return DateUtils.isToday(getDateFromServer(time)!!.time)
    }

    fun isToday(date: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = sdf.parse(date)
        return DateUtils.isToday(dateTime!!.time)
    }

    fun isTomorrow(date: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = sdf.parse(date)
        val currentCalendar = Calendar.getInstance()
        val serverCalendar = Calendar.getInstance()
        serverCalendar.timeInMillis = dateTime!!.time
        return currentCalendar[Calendar.YEAR] == serverCalendar[Calendar.YEAR] && currentCalendar[Calendar.DAY_OF_YEAR] == serverCalendar[Calendar.DAY_OF_YEAR] - 1
    }

    @Throws(Exception::class)
    fun isThisWeek(date: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = sdf.parse(date)
        val currentCalendar = Calendar.getInstance()
        var serverCalendar = Calendar.getInstance()
        serverCalendar.timeInMillis = dateTime!!.time
//        Logger.log(
//            "Week",
//            "${currentCalendar[Calendar.WEEK_OF_YEAR]},${serverCalendar[Calendar.WEEK_OF_YEAR]}"
//        )
        return currentCalendar[Calendar.YEAR] == serverCalendar[Calendar.YEAR] && currentCalendar[Calendar.WEEK_OF_YEAR] == serverCalendar[Calendar.WEEK_OF_YEAR]
    }

    @Throws(Exception::class)
    fun isNextWeek(date: String, format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = sdf.parse(date)
        val currentCalendar = Calendar.getInstance()
        var serverCalendar = Calendar.getInstance()
        serverCalendar.timeInMillis = dateTime!!.time
        return currentCalendar[Calendar.YEAR] == serverCalendar[Calendar.YEAR] && currentCalendar[Calendar.WEEK_OF_YEAR] == serverCalendar[Calendar.WEEK_OF_YEAR] - 1
    }

    fun setFromDateToStart(calendarFrom: Calendar) {
        calendarFrom.set(Calendar.HOUR_OF_DAY, 0)
        calendarFrom.set(Calendar.MINUTE, 0)
        calendarFrom.set(Calendar.SECOND, 0)
    }

    fun setToDateToEnd(calendarFrom: Calendar) {
        calendarFrom.set(Calendar.HOUR_OF_DAY, 23)
        calendarFrom.set(Calendar.MINUTE, 59)
        calendarFrom.set(Calendar.SECOND, 59)
    }

    fun getToDayFormatTh(): String {
        return getDateFormatTh(Date().time)
    }

    fun getDateFormatTh(time: Long): String {
        var format = SimpleDateFormat("d", Locale.getDefault())
        val date = format.format(time)

        format =
            if (date.endsWith("1") && !date.endsWith("11")) SimpleDateFormat(
                "d'st' MMM, yyyy",
                Locale.getDefault()
            ) else if (date.endsWith(
                    "2"
                ) && !date.endsWith(
                    "12"
                )
            ) SimpleDateFormat(
                "d'nd' MMM , yyyy",
                Locale.getDefault()
            ) else if (date.endsWith("3") && !date.endsWith(
                    "13"
                )
            ) SimpleDateFormat(
                "d'rd' MMM, yyyy",
                Locale.getDefault()
            ) else SimpleDateFormat("d'th' MMM, yyyy", Locale.getDefault())

        return format.format(time)
    }

    @Throws(Exception::class)
    fun dateFormat(
        dateFromServer: String,
        formate: String = DateFormat.DEFAULT_DATE_FORMAT
    ): String {
        var date = dateFromServer
//        pattern server
        var spf = SimpleDateFormat(DateFormat.DATE_FORMAT_5, Locale.getDefault())
        val newDate: Date = spf.parse(date)
//        new format
        spf = SimpleDateFormat(formate, Locale.getDefault())
        date = spf.format(newDate)
        return date

    }

    fun getNextWeekSaturday(): Long {
        val calendar = Calendar.getInstance()
        val calendarWeek = Calendar.getInstance()
        calendarWeek[Calendar.WEEK_OF_YEAR] = calendar[Calendar.WEEK_OF_YEAR] + 1
        calendarWeek[Calendar.DAY_OF_WEEK] = Calendar.SATURDAY
        return calendarWeek.timeInMillis
    }

    fun getLastWeekStart(): Long {
        val calendar = Calendar.getInstance()
        val calendarWeek = Calendar.getInstance()
        calendarWeek[Calendar.WEEK_OF_MONTH] = calendar[Calendar.WEEK_OF_MONTH] - 1
        calendarWeek[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        return calendarWeek.timeInMillis
    }


    fun calCalculateTime(start: Long, end: Long): String {
        val differMilli = abs(start - end)
        val totalSec = differMilli / 1000

        val totalMinute = totalSec / 60
        val totalHour = totalMinute / 60
        val minute = totalMinute.rem(60)
        return "${DecimalFormat("00").format(totalHour)} hr : ${DecimalFormat("00").format(minute)} mn"
    }

    fun parseDayOfWeek(day: String, pattern: String): Int {
        val dayFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = dayFormat.parse(day)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.DAY_OF_WEEK]
    }

}