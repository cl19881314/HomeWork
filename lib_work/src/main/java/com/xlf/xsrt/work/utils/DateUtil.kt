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
        fun string2date(dateString: String): Date? {
            var formateDate: Date? = null
            val format = SimpleDateFormat(YYYY_MM_DD)
            try {
                formateDate = format.parse(dateString)
            } catch (e: ParseException) {
                return null
            }

            return formateDate
        }

        /**
         * 日期转化为字符串
         *  yyyy-MM-dd格式
         */
        fun dateToString(date: Date): String {
            val sdf = SimpleDateFormat(YYYY_MM_DD)
            return sdf.format(date)
        }

        /**
         * 日期转化为字符串
         *  yyyy-MM格式
         */
        fun dateToString2(date: Date): String {
            val sdf = SimpleDateFormat(YYYY_MM)
            return sdf.format(date)
        }

        /**
         *  xxxx年xx月xx日转换
         *  返回 yyyy-MM-dd格式
         *
         */
        fun chainToString2(date: String): String {
            var time = date.replace("年","-").replace("月","-").replace("日","")
            return time
        }
    }
}