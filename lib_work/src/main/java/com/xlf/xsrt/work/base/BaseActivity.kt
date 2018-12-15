package com.xlf.xsrt.work.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeDefaultTitle()
        setContentView(getContentViewId())
        init()
        initListener()
    }

    private fun removeDefaultTitle() {
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //去掉状态栏
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun initListener() {}

    protected abstract fun getContentViewId(): Int
    protected abstract fun init()
}