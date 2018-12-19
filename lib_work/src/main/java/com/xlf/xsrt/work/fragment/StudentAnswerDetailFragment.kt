package com.xlf.xsrt.work.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseFragment
import kotlinx.android.synthetic.main.xsrt_item_answer_option.view.*
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
        detailWeb!!.loadUrl("https://www.baidu.com")
        for (i in 0..4) {
            var optionView = LayoutInflater.from(activity).inflate(R.layout.xsrt_item_answer_option, null, false)
            var txt = (i + 65).toChar()
            optionView.optionTxt.text = txt.toString()
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            optionView.layoutParams = params
            optionView.optionTxt.setOnClickListener {

            }
            if (i == 1){
                optionView.optionTxt.setBackgroundResource(R.drawable.xsrt_answer_choosed)
                optionView.optionTxt.setTextColor(resources.getColor(R.color.xsrt_white))
            } else {
                optionView.optionTxt.setBackgroundResource(R.drawable.xsrt_answer_normal)
                optionView.optionTxt.setTextColor(resources.getColor(R.color.xsrt_btn_bg_color))
            }
            chooseAnswerLL.addView(optionView)
        }
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