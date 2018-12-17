package com.xlf.xsrt.work.detail

import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import kotlinx.android.synthetic.main.xsrt_activity_subject_detail_layout.*

class SubjectDetailActivity : BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_subject_detail_layout
    }
    override fun init() {
        var settings = showContentWeb.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        showContentWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        showContentWeb.loadUrl("https://www.baidu.com")
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
    }

}