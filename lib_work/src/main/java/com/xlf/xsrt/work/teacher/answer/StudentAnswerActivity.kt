package com.xlf.xsrt.work.teacher.answer

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerAdapter
import kotlinx.android.synthetic.main.activity_student_answer_layout.*

class StudentAnswerActivity : BaseActivity(){
    private var mAdapter : StudentAnswerAdapter ?= null
    override fun getContentViewId(): Int {
        return R.layout.activity_student_answer_layout
    }

    override fun init() {
        backImg.setOnClickListener {
            finish()
        }

        classNameTxt.setOnClickListener {

        }
        mAdapter = StudentAnswerAdapter()
        showDataRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        showDataRv.adapter = mAdapter

        showDataRv.setOnLoadMoreListener {

        }
    }

}