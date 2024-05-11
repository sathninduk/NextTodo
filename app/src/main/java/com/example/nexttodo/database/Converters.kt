package com.example.nexttodo.database

import androidx.room.TypeConverter
import java.sql.Date

// Converters class is a class that converts Date objects to Long and vice versa.
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? { // Converts a Long value to a Date object
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? { // Converts a Date object to a Long value
        return date?.time
    }
}