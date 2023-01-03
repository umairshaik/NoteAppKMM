package com.example.noteappkmm.domain.time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {

    fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    fun toEpochMillis(dateTime: LocalDateTime): Long =
        dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    fun formatNoteDate(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.take(3).lowercase()
            .replaceFirstChar { it.uppercase() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val minutes = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute
        return "$month $day $year, $hour:$minutes"
    }
}