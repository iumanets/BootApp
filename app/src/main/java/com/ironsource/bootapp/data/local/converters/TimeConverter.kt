package com.ironsource.bootapp.data.local.converters

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

//We could easily use LocalDateTime or Instant but we need to target below API 24
class TimeConverter {
    @TypeConverter
    fun longToDate(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    fun longToCalendar(value: Long?): Calendar? = value?.let {
        Calendar.getInstance().apply { timeInMillis = value }
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar?): Long? = calendar?.timeInMillis
}