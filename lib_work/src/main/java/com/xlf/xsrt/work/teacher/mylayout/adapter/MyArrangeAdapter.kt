package com.xlf.xsrt.work.teacher.mylayout.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import com.xlf.xsrt.work.detail.SubjectDetailActivity
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter
import kotlinx.android.synthetic.main.xsrt_item_subject_layout.view.*

class MyArrangeAdapter : XXXAdapter<MyArrangeAdapter.ArrangeHolder>() {
    private var mData: ArrayList<HomeworkBaseVo> = ArrayList()
    fun setArrangeData(data: ArrayList<HomeworkBaseVo>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getRealItemCount(): Int {
        return mData.size
//        return 10
    }

    override fun onRealCreateViewHolder(parent: ViewGroup?, viewType: Int): ArrangeHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.xsrt_item_subject_layout, parent, false)
        return ArrangeHolder(view)
    }

    override fun onRealBindViewHolder(holder: ArrangeHolder?, position: Int) {
        holder!!.bindTo(position)
    }


    inner class ArrangeHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bindTo(position: Int) {
            var bean = mData[position]
            view.subjectNumTxt.text = String.format("编号:%s", bean.homeworkId)
            view.fl_subject_item.setOnClickListener {
                var intent = Intent(itemView.context, SubjectDetailActivity::class.java)
                intent.putExtra("url", bean.homeworkDetailUrl)
                intent.putExtra("num", bean.homeworkId.toString())
                itemView.context.startActivity(intent)
            }
            var webview = view.showContentWeb
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
//            var url = "http://192.168.1.194:8080/homework/teacherManage/stuAnswerDetail/9"
//            webview.loadUrl(url)
        }
    }
}
