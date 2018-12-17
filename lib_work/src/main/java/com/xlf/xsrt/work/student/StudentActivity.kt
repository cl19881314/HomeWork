package com.xlf.xsrt.work.student

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.student.adapter.StudentAdapter
import com.xlf.xsrt.work.widget.TitleBar
import com.xlf.xsrt.work.widget.calendar.WCanlendarView
import kotlinx.android.synthetic.main.xsrt_activity_student.*

class StudentActivity : BaseActivity() {

    private val mAdapter by lazy {
        StudentAdapter()
    }

    private val mCanlendarView by lazy {
        WCanlendarView(this)
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student
    }

    override fun init() {
        rcy_student.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_student.adapter = mAdapter
        rcy_student.addHeaderView(mCanlendarView)
    }

    override fun initListener() {
        titlebar_student.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
    }

}