package com.xlf.xsrt.homework

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * @author Chenhong
 * @date 2018/12/25.
 * @des
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}