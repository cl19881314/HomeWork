package com.xlf.xsrt.work.fragment

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseFragment
import kotlinx.android.synthetic.main.xsrt_item_student_answer_detail_layout.*

class StudentAnswerDetailFragment : BaseFragment() {

    override fun getContentViewId(): Int {
        return R.layout.xsrt_item_student_answer_detail_layout
    }

    override fun init(view: View) {
        var settings = detailWeb!!.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        detailWeb!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        var url = arguments?.getString("url")
//        var url = "http://192.168.1.194:8080/homework/teacherManage/stuAnswerDetail/9"
        detailWeb!!.loadUrl(url)

    }


    override fun onDestroy() {
        try {
            if (detailWeb != null) {
                val parent = detailWeb!!.parent as ViewGroup
                parent?.removeView(detailWeb)
                detailWeb!!.stopLoading()
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                detailWeb!!.settings.javaScriptEnabled = false
                detailWeb!!.clearHistory()
                detailWeb!!.clearView()
                detailWeb!!.removeAllViews()
                detailWeb!!.destroy()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }
}