package com.xlf.xsrt.work.teacher.answer

import android.content.Intent
import android.support.v4.app.Fragment
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import kotlinx.android.synthetic.main.xsrt_activity_answer_detail_layout.*

class StudentAnswerDetailActivity : BaseActivity() {
    private var mAdapter: StudentAnswerDetailAdapter? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_answer_detail_layout
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