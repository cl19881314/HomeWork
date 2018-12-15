package com.xlf.xsrt.work.teacher.answer

import android.content.Intent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import com.xlf.xsrt.work.teacher.answer.fragment.StudentAnswerDetailFragment
import kotlinx.android.synthetic.main.activity_answer_detail_layout.*
import kotlinx.android.synthetic.main.item_student_answer_detail_layout.view.*

class StudentAnswerDetailActivity : BaseActivity() {
    private var mAdapter: StudentAnswerDetailAdapter? = null
    override fun getContentViewId(): Int {
        return R.layout.activity_answer_detail_layout
    }

    override fun init() {
        mAdapter = StudentAnswerDetailAdapter(supportFragmentManager)
        detailViewPager.adapter = mAdapter

        getDetailData()

        toDoCommentTxt.setOnClickListener {
            var intent = Intent(this@StudentAnswerDetailActivity, WriteCommentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetailData() {
        var data = ArrayList<Fragment>()
        for (i in 0..10) {
            var fragment = StudentAnswerDetailFragment()
         /*   if (i % 2 == 0) {
                web.loadUrl("https://www.baidu.com")
            } else if (i % 3 == 0){
                web.loadUrl("https://www.cnblogs.com/jzyhywxz/p/6914307.html")
            } else {
                web.loadUrl("https://www.duba.com/?f=liebao")
            }*/
            data.add(fragment)
        }
        mAdapter?.setData(data)
    }

}