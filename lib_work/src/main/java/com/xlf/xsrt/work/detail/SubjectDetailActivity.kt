package com.xlf.xsrt.work.detail

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import kotlinx.android.synthetic.main.xsrt_activity_subject_detail_layout.*

/**
 * 教师端作业题目详情界面
 */
class SubjectDetailActivity : BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_subject_detail_layout
    }
    override fun init() {
        var url = intent.getStringExtra("url")
        var num = intent.getStringExtra("num")
        titleBar.setTitleTxt("编号:$num")

        var settings = showContentWeb.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        showContentWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        showContentWeb.loadUrl(url)
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        try {
            if (showContentWeb != null) {
                val parent = showContentWeb!!.parent as ViewGroup
                parent?.removeView(showContentWeb)
                showContentWeb!!.stopLoading()
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                showContentWeb!!.settings.javaScriptEnabled = false
                showContentWeb!!.clearHistory()
                showContentWeb!!.clearView()
                showContentWeb!!.removeAllViews()
                showContentWeb!!.destroy()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

}