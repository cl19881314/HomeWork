package com.xlf.xsrt.work.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

open class ScreenUtil {
    companion object {
        /**
         * 获得屏幕宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        /**
         * 获得屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }
    }
}