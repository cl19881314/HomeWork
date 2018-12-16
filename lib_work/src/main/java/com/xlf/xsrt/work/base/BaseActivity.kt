package com.xlf.xsrt.work.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity() {
    open val RESPONSE_SUCCESS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeDefaultTitle()
        setContentView(getContentViewId())
        init()
        doResponseData()
        initListener()
    }

    open fun doResponseData() {
    }

    private fun removeDefaultTitle() {
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //去掉状态栏
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    open fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    open fun initListener() {}

    protected abstract fun getContentViewId(): Int
    protected abstract fun init()
}