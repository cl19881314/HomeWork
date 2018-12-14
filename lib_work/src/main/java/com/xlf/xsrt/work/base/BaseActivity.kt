package com.xlf.xsrt.work.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
        init()
        initListener()
    }

    private fun initListener() {}

    protected abstract fun getContentViewId(): Int
    protected abstract fun init()
}