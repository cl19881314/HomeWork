package com.xlf.xsrt.work.teacher.answer.fragment

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseFragment
import kotlinx.android.synthetic.main.xsrt_item_student_answer_detail_layout.view.*

class StudentAnswerDetailFragment : BaseFragment() {
    private var mWebView: WebView? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_item_student_answer_detail_layout
    }

    override fun init(view: View) {
        mWebView = view.detailWeb
        var settings = mWebView!!.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        mWebView!!.loadUrl("https://www.baidu.com")
    }


    override fun onDestroy() {
        //TODO recyler webview
        try {
            if (mWebView != null) {
                val parent = mWebView!!.parent as ViewGroup
                parent?.removeView(mWebView)
                mWebView!!.stopLoading()
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                mWebView!!.settings.javaScriptEnabled = false
                mWebView!!.clearHistory()
                mWebView!!.clearView()
                mWebView!!.removeAllViews()
                mWebView!!.destroy()
                mWebView = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }
}