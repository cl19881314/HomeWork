package com.xlf.xsrt.work.teacher.answer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerAdapter
import com.xlf.xsrt.work.teacher.answer.viewmodel.StudentAnswerViewModel
import kotlinx.android.synthetic.main.xsrt_activity_student_answer_layout.*

/**
 * 学生作业
 */
class StudentAnswerActivity : BaseActivity(){
    private var mAdapter : StudentAnswerAdapter ?= null

    private val mDataViewModel by lazy {
        ViewModelProviders.of(this@StudentAnswerActivity).get(StudentAnswerViewModel::class.java)
    }
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student_answer_layout
    }

    override fun init() {
        //mDataViewModel.getStudentAnswerData(mUserId, -1,"", -1)
        mAdapter = StudentAnswerAdapter()
        showDataRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        showDataRv.adapter = mAdapter
    }

    override fun doResponseData() {
        mDataViewModel.mAnswerViewModel.observe(this, Observer{
            if (it?.flag == 1){
                mAdapter!!.setAnswerData(it?.homeworkList!![0].stuAnswerList)
            }
        })
    }

    override fun initListener() {
        backImg.setOnClickListener {
            finish()
        }

        classNameTxt.setOnClickListener {

        }
        showDataRv.setOnLoadMoreListener {

        }
    }

}