package com.comnet.androidtest.utils

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.sql.Timestamp
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DateUtils {

    companion object {
        const val DEFAULT_FORMAT_DATE_WITHOUT_TIME = "yyyy-MM-dd"
        const val DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss"
        const val FORMAT_DATE_1 = "MM/dd/yyyy" // 04/13/2015
        const val FORMAT_DATE_2 = "EEE, MMM d, ''yy" // Wed, Jul 4, '01
        const val FORMAT_DATE_3 = "h:mm a" // 12:08 PM
        const val FORMAT_DATE_4 = "dd-M-yyyy hh:mm:ss" // 	13-4-2015 10:59:26
        const val FORMAT_DATE_5 = "dd MMMM yyyy" // 	13 April 2015
        const val FORMAT_DATE_6 = "dd MMMM yyyy zzzz" // 13 April 2015 India Standard Time
        const val FORMAT_DATE_7 = "EEE, d MMM yyyy HH:mm:ss Z" // Wed, 4 Jul 2001 12:08:56 -0700
        const val FORMAT_DATE_8 = "E, dd MMM yyyy HH:mm:ss z" // Mon, 13 Apr 2015 22:59:26 IST
        const val FORMAT_DATE_9 = "yyyy.MM.dd G 'at' HH:mm:ss z" // 2001.07.04 AD at 12:08:56 PDT
        const val FORMAT_DATE_10 = "yyyy-MM-dd'T'HH:mm:ss'Z'" // 2001-07-04T12:08:56.235-0700
    }

    val BASETIME = BigDecimal("60.00")
    val DATE_PATTERN =
        Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])([-/.]?)(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0?[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2([-/.]?)29)$")

    fun validateDateFormat(dateText: String?): Boolean {
        return DATE_PATTERN.matcher(dateText).matches()
    }

    fun now(): Timestamp {
        return Timestamp(System.currentTimeMillis())
    }

    fun resetTime(date: Date?): Date {
        val c = Calendar.getInstance()
        c.time = date
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        return c.time
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date?, formatStr: String?): String {
        return SimpleDateFormat(formatStr ?: Companion.DEFAULT_FORMAT_DATE).format(
            date!!
        )
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    fun formatDate(dateStr: String?, formatStr: String?): Date {
        return SimpleDateFormat(formatStr ?: Companion.DEFAULT_FORMAT_DATE).parse(
            dateStr!!
        )!!
    }

    fun formatTime(dateStr: String?): Timestamp {
        return Timestamp.valueOf(dateStr)
    }

    fun getYear(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.YEAR]
    }

    @Throws(ParseException::class)
    fun getYear(dateStr: String?, format: String?): Int {
        return getYear(
            formatDate(
                dateStr,
                format
            )
        )
    }

    fun getMonth(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.MONTH] + 1
    }

    @Throws(ParseException::class)
    fun getMonth(dateStr: String?, format: String?): Int {
        return getMonth(
            formatDate(
                dateStr,
                format
            )
        )
    }

    fun getDay(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.DAY_OF_MONTH]
    }

    @Throws(ParseException::class)
    fun getDay(dateStr: String?, format: String?): Int {
        return getDay(
            formatDate(
                dateStr,
                format
            )
        )
    }

    fun getWeekDay(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.DAY_OF_WEEK]
    }

    @Throws(ParseException::class)
    fun getWeekDay(dateStr: String?, format: String?): Int {
        return getWeekDay(
            formatDate(
                dateStr,
                format
            )
        )
    }

    fun getDayCountInMonth(month: Int, isLeapYear: Boolean): Int {
        var dayCount = 0
        if (month >= Calendar.JANUARY && month <= Calendar.DECEMBER) {
            if (month == Calendar.JANUARY || month == Calendar.MARCH || month == Calendar.MAY || month == Calendar.JULY || month == Calendar.AUGUST || month == Calendar.OCTOBER || month == Calendar.DECEMBER
            ) {
                dayCount = 31
            } else if (month == Calendar.APRIL || month == Calendar.JUNE || month == Calendar.SEPTEMBER || month == Calendar.NOVEMBER
            ) {
                dayCount = 30
            } else if (month == Calendar.FEBRUARY) {
                dayCount = if (isLeapYear) {
                    29
                } else {
                    28
                }
            }
        }
        return dayCount
    }

    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 || year % 100 != 0 && year % 400 == 0
    }

    fun compareDay(d1: Date?, d2: Date?): Boolean {
        return resetTime(d1)
            .after(resetTime(d2))
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    fun daySub(startDate: Date, endDate: Date): Long {
        return if (startDate.time - endDate.time > 0) (startDate
            .time - endDate.time) / 86400000 else (endDate.time - startDate.time) / 86400000
    }

    fun addOneDay(date: Date?): Date {
        val c = Calendar.getInstance()
        c.time = date
        c[Calendar.DAY_OF_MONTH] = c[Calendar.DAY_OF_MONTH] + 1
        return resetTime(c.time)
    }

    //减少一天
    fun lessenOneDay(date: Date?): Date {
        val c = Calendar.getInstance()
        c.time = date
        c[Calendar.DAY_OF_MONTH] = c[Calendar.DAY_OF_MONTH] - 1
        return resetTime(c.time)
    }

    @Throws(Exception::class)
    fun lessenOneDay(dateStr: String?): String {
        val date = formatDate(
            dateStr,
            Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
        )
        val c = Calendar.getInstance()
        c.time = date
        c[Calendar.DAY_OF_MONTH] = c[Calendar.DAY_OF_MONTH] - 1
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        return df.format(resetTime(c.time))
    }

    fun getFormatDateWithoutMillSecond(time: String?): String? {
        return if (time == null || time.trim { it <= ' ' }.isEmpty()) {
            null
        } else time.substring(0, 19)
    }

    /*
	 * Get start time by year and month
	 */
    fun getDateFromYearAndMonth(year: Int, month: Int): Date? {
        var year = year
        var month = month
        if (month > 12) {
            year++
            month -= 12
        }
        var resultDate = Date(year - 1900, month - 1, 1)
        resultDate = resetTime(resultDate)
        return resultDate
    }

    fun getDateFromSeason(year: Int, season: Int): Date? {
        var year = year
        var season = season
        if (season < 1) {
            throw RuntimeException("must be greater than 1")
        }
        if (season > 4) {
            year += season / 4
            season %= 4
        }
        val month = (season - 1) * 3 + 1
        return getDateFromYearAndMonth(year, month)
    }

    fun getLastMomentOfDay(original: Date?): Date {
        val calendar = Calendar.getInstance()
        calendar.time = original
        calendar[Calendar.HOUR_OF_DAY] = calendar.getActualMaximum(Calendar.HOUR_OF_DAY)
        calendar[Calendar.MINUTE] = calendar.getActualMaximum(Calendar.MINUTE)
        calendar[Calendar.SECOND] = calendar.getActualMaximum(Calendar.SECOND)
        calendar[Calendar.MILLISECOND] = 0 // If this exceeds 0, it will be carried and will become the next day
        return calendar.time
    }

    /**
     * Format (from, start time, etc.) time to facilitate hql time query comparison
     */
    fun formateStartTime(startTime: String): String {
        return "$startTime 00:00:00"
    }

    /**
     * Format (to, end time, etc.) time to facilitate hql time query comparison
     */
    fun formateEndTime(endTime: String): String {
        return "$endTime 23:59:59"
    }

    /**
     * Get the date string of the last day of a year and month (such as 2013-08-31)
     */
    fun getLastDayText(year: Int?, month: Int?): String {
        return formatDate(
            getLastDay(
                year,
                month
            ),
            "${Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME} 23:59:59"
        )
    }

    fun getLastDay(year: Int?, month: Int?): Date {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year!!
        cal[Calendar.MONTH] = month!!
        cal[Calendar.DAY_OF_MONTH] = 1
        cal.add(Calendar.DAY_OF_MONTH, -1)
        return cal.time
    }

    //The last day of a month
    fun getLastDay(dateStr: String?): String {
        val cal = Calendar.getInstance()
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        try {
            val date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            cal.time = date
            cal.add(Calendar.MONTH, 1)
            cal[Calendar.DAY_OF_MONTH] = 1
            cal.add(Calendar.DAY_OF_MONTH, -1)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return df.format(cal.time)
    }

    /**
     *Get the date string of the first day of a year and month (such as 2013-08-01)
     */
    fun getFirstDayText(year: Int, month: Int): String {
        return if (month < 10) {
            "$year-0$month-01 00:00:00"
        } else "$year-$month-01 00:00:00"
    }

    fun getFirstDay(year: Int, month: Int): Date? {
        return try {
            formatDate(
                "$year-$month-1",
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //The first day of a month
    fun getFirstDay(dateStr: String?): String {
        val cal = Calendar.getInstance()
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        try {
            val date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            cal.time = date
            cal[Calendar.DAY_OF_MONTH] = 1
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return df.format(cal.time)
    }

    /**
     * Get a date before a month
     */
    fun getBeforeMonthDate(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, -1)
        return cal.time
    }

    fun addOneSecond(time: Date): Date {
        return Timestamp(time.time + 1000)
    }

    /**
     * Take the first day of the week of the current day, Sunday
     * @param args
     */
    fun getFirstWeekDay(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY // Sunday of the first day of the week
        return cal.time
    }

    fun getFirstWeekDay(dateStr: String?): Date? {
        var date: Date? = null
        try {
            date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            date = getFirstWeekDay(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getFirstWeekDayStr(dateStr: String?): String {
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        return df.format(getFirstWeekDay(dateStr))
    }

    /**
     * Take the last day of the week of the current day, Saturday
     * @param args
     */
    fun getLastWeekDay(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.DAY_OF_WEEK] = Calendar.SATURDAY //Saturday of the last day of the week
        return cal.time
    }

    fun getLastWeekDay(dateStr: String?): Date? {
        var date: Date? = null
        try {
            date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            date = getLastWeekDay(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getLastWeekDayStr(dateStr: String?): String {
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        return df.format(getLastWeekDay(dateStr))
    }

    /**
     * Take the first day of the quarter
     * @param args
     */
    fun getFirstQuarter(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        val month = getQuarterInMonth(
            cal[Calendar.MONTH],
            true
        )
        cal[Calendar.MONTH] = month - 1
        cal[Calendar.DAY_OF_MONTH] = 1
        return cal.time
    }

    fun getFirstQuarter(dateStr: String?): Date? {
        var date: Date? = null
        try {
            date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            date = getFirstQuarter(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getFirstQuarterStr(dateStr: String?): String {
        val df =
            SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME, Locale.getDefault())
        return df.format(getFirstQuarter(dateStr))
    }

    /**
     *Take the last day of the quarter on the date
     * @param args
     */
    fun getLastQuarter(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        val month = getQuarterInMonth(
            cal[Calendar.MONTH],
            false
        )
        cal[Calendar.MONTH] = month
        cal[Calendar.DAY_OF_MONTH] = 1
        cal.add(Calendar.DAY_OF_MONTH, -1)
        return cal.time
    }

    fun getLastQuarter(dateStr: String?): Date? {
        var date: Date? = null
        try {
            date = formatDate(
                dateStr,
                Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME
            )
            date = getLastQuarter(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getLastQuarterStr(dateStr: String?): String {
        val df = SimpleDateFormat(Companion.DEFAULT_FORMAT_DATE_WITHOUT_TIME,Locale.getDefault())
        return df.format(getLastQuarter(dateStr)!!)
    }

    //Returns the first few months, not several months
    // Quarters throughout the year, first quarter: February-April, second quarter: May-July, third quarter: August-October, fourth quarter: November-January
    private fun getQuarterInMonth(month: Int, isQuarterStart: Boolean): Int {
        var months = intArrayOf(1, 4, 7, 10)
        if (!isQuarterStart) {
            months = intArrayOf(3, 6, 9, 12)
        }
        return if (month in 0..2) months[0] else if (month in 3..5) months[1] else if (month in 6..8) months[2] else months[3]
    }

    /**
     * String to timestamp
     */
    @Throws(Exception::class)
    fun formatTimestamp(time: String?, formatStr: String?): Timestamp {
        val date =
            formatDate(time, formatStr)
        return Timestamp(date.time)
    }
    /**
     * date to timestamp
     */
    @Throws(Exception::class)
    fun formatTimestamp(date: Date): Timestamp {
        return Timestamp(date.time)
    }


    /**
     * Convert date in string format to another string format
     *
     * @param stringDate
     * @param inputFormat
     * @param outFormat
     * @return
     */
    fun convertStringDate(stringDate: String?, inputFormat: String?, outFormat: String?): String {
        var strDateTime = ""
        try {
            val inputFormatter: DateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
            val da: Date = inputFormatter.parse(stringDate) as Date
            //            System.out.println("==Date is ==" + da);
            val outputFormatter: DateFormat = SimpleDateFormat(outFormat, Locale.getDefault())
            strDateTime = outputFormatter.format(da)
            //            System.out.println("==String date is : " + strDateTime);
            return strDateTime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return strDateTime
    }

    /**
     * Get Hours from string date
     *
     * @param stringdate
     * @return
     */
    fun getHoursFromStringDate(stringdate: String?, inputFormat: String?): Int {
        var hours_24 = 0
        try {
            val inputFormatter: DateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
            val date: Date = inputFormatter.parse(stringdate) as Date
            hours_24 = date.getHours() // int
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return hours_24
    }


    fun getYesterdayDate(cal: Calendar): String{
        cal.add(Calendar.DATE, -1)
        return DateUtils().formatDate(cal.time, DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME)
    }

}


