package com.xlf.xsrt.work.student

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.student.adapter.StudentAdapter
import com.xlf.xsrt.work.widget.TitleBar
import com.xlf.xsrt.work.widget.calendar.WCanlendarView
import kotlinx.android.synthetic.main.xsrt_activity_student.*

/**
 * 学生主界面
 */
class StudentActivity : BaseActivity() {

    private val mAdapter by lazy {
        StudentAdapter()
    }


    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student
    }

    override fun init() {
        rcy_student.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_student.adapter = mAdapter
    }

    override fun initListener() {
        titlebar_student.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })

        ibtn_premonth_stu.setOnClickListener { calendar_stu.moveToPreMonth() }
        ibtn_nextmonth_stu.setOnClickListener { calendar_stu.moveToNextMonth() }
        calendar_stu.setOnCanlendarPageSelectListener { year, month ->
            tv_time_stu.text = "${year}年${month}月"
            //TODO:拉取下个月数据
        }
        calendar_stu.setOnCanlendarItemListener { calendar, position ->
            //TODO:拉去当天数据
        }

    }

}