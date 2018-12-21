package com.xlf.xsrt.work.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        private const val YYYY_MM_DD = "yyyy-MM-dd"
        private const val YYYY_MM = "yyyy-MM"
        /**
         * 字符串转化为日期
         * 格式 "yyyy-MM-dd"
         */
        fun string2date(dateString: String, format: String): Date? {
            var formateDate: Date? = null
            val format = SimpleDateFormat(format)
            try {
                formateDate = format.parse(dateString)
            } catch (e: ParseException) {
                return null
            }

            return formateDate
        }

        /**
         * 日期转化为字符串
         */
        fun dateToString(date: Date,format: String): String {
            val sdf = SimpleDateFormat(format)
            return sdf.format(date)
        }

        /**
         *  xxxx年xx月xx日转换
         *  返回 yyyy-MM-dd格式
         *
         */
        fun chainToString2(date: String): String {
            return date.replace("年", "-").replace("月", "-").replace("日", "")
        }
    }
}