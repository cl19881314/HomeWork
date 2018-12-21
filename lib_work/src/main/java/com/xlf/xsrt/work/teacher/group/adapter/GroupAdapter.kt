package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import kotlinx.android.synthetic.main.xsrt_item_subject_layout.view.*

class GroupAdapter : BaseRcyAdapter<HomeworkBaseVo>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_subject_layout
    }

    override fun setItemContent(itemView: View, bean: HomeworkBaseVo, positon: Int, listener: ItemChildViewClickListener?) {
        //添加
        itemView.isAdded.visibility = View.VISIBLE
        itemView.isAdded.isChecked = bean.addFlag == 1
        itemView.isAdded.setOnClickListener {
            listener?.onItemChildClick(it, positon)
        }
        itemView.subjectNumTxt.text = "编号${bean.homeworkId}"
        //详情
        itemView.showDetailTxt.setOnClickListener {
            listener?.onItemChildClick(it, positon)
        }
        //内容
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
        //收藏
        itemView.isCollocted.visibility = View.VISIBLE
        if(bean.collectFlag==1){
            itemView.isCollocted.setBackgroundResource(R.drawable.xsrt_ic_collection_yes)
        }else{
            itemView.isCollocted.setBackgroundResource(R.drawable.xsrt_ic_collection_no)
        }
        itemView.isCollocted.setOnClickListener {
            listener?.onItemChildClick(it, positon)
        }
    }


}