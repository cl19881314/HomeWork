package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import kotlinx.android.synthetic.main.xsrt_item_subject_layout.view.*

class SelectedHomeworkAdapter : BaseRcyAdapter<HomeworkBaseVo>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_subject_layout

    }

    override fun setItemContent(itemView: View, bean: HomeworkBaseVo, positon: Int, listener: ItemChildViewClickListener?) {
        itemView.subjectNumTxt.text = String.format("编号:%s", bean.homeworkId)
        itemView.showDetailTxt.setOnClickListener {
            listener?.onItemChildClick(it, positon)
        }
        itemView.dellImg.visibility = View.VISIBLE
        itemView.dellImg.setOnClickListener {
            //取消
            listener?.onItemChildClick(it, positon)
        }
        var webview = itemView.showContentWeb
        var settings = webview.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webview.loadUrl(bean.homeworkContentUrl)

    }
}