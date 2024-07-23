package com.walkS.yiprogress.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtils {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * 将Unix时间戳转换为LocalDateTime
     * @param timestamp Unix时间戳（秒）
     * @return LocalDateTime
     */
    fun timestampToDateTime(timestamp: Long): LocalDateTime {
        return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    /**
     * 将LocalDateTime转换为Unix时间戳（秒）
     * @param dateTime LocalDateTime
     * @return Unix时间戳（秒）
     */
    fun dateTimeToTimestamp(dateTime: LocalDateTime): Long {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond
    }

    /**
     * 将LocalDateTime格式化为字符串
     * @param dateTime LocalDateTime
     * @return 格式化的日期时间字符串
     */
    fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

    /**
     * 将字符串解析为LocalDateTime
     * @param dateString 日期时间字符串
     * @return LocalDateTime
     */
    fun parseDateTime(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString, formatter)
    }

    /**
     * 将Date转换为LocalDateTime
     * @param date Date
     * @return LocalDateTime
     */
    fun dateToLocalDateTime(date: Date): LocalDateTime {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    /**
     * 将LocalDateTime转换为Date
     * @param dateTime LocalDateTime
     * @return Date
     */
    fun localDateTimeToDate(dateTime: LocalDateTime): Date {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}
