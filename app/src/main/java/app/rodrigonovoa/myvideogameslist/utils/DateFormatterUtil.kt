package app.rodrigonovoa.myvideogameslist.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatterUtil {
    fun fromDateStringToTimeStamp(date:String):Long{
        val formatedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        return formatedDate.time
    }

    fun fromTimeStampToDateString(date: Long):String{
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(date)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return ""
        }
    }
}