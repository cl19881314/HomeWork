package com.xlf.xsrt.work.teacher.answer.fragment

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseFragment
import kotlinx.android.synthetic.main.item_student_answer_detail_layout.view.*

class StudentAnswerDetailFragment : BaseFragment(){
    override fun getContentViewId(): Int {
        return R.layout.item_student_answer_detail_layout
    }

    override fun init(view : View) {
        var web = view.detailWeb
        var settings = web.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        web.loadUrl("https://www.baidu.com")
    }


    override fun onDestroy() {
        //TODO recyler webview
        super.onDestroy()
    }
}