package com.xlf.xsrt.work.teacher.answer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerAdapter
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.teacher.answer.viewmodel.StudentAnswerViewModel
import com.xlf.xsrt.work.widget.pulltextview.PullBean
import com.xlf.xsrt.work.widget.pulltextview.PullTextView
import kotlinx.android.synthetic.main.xsrt_activity_student_answer_layout.*

/**
 * 学生作业
 */
class StudentAnswerActivity : BaseActivity() {
    private var mAdapter: StudentAnswerAdapter? = null
    private var mClassId = -1
    private var mTimeId = ""
    private var mWorkId = -1
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
        mDataViewModel.mAnswerViewModel.observe(this, Observer {
            if (it?.flag == 1) {
                mAdapter!!.setAnswerData(it?.stuAnswerList)
                if (it?.classList?.size ?: -1 > 0) {
                    addClassPullData(it?.classList)
                    addTimePullData(it?.classList)
                }
                if (it?.homeworkList?.size ?: -1 > 0) {
                    addWorkPullData(it?.homeworkList!!)
                }
            }
        })
    }

    private fun addWorkPullData(homeworkList: ArrayList<StudentAnswerBean.HomeworkVo>) {
        var list = mutableListOf<PullBean>()
        for (bean in homeworkList!!) {
            var data = PullBean()
            data.searchId = bean.homeworkId
            data.content = bean.homeworkName
            list.add(data)
        }
        homeWorkPullTxt.updateData(list, true)
    }

    private fun addTimePullData(classList: ArrayList<StudentAnswerBean.ClassVo>?) {
        var list = mutableListOf<PullBean>()
        for (i in classList!!.indices) {
            var data = PullBean()
            data.searchId = i
            data.content = classList[i].createTime
            list.add(data)
        }
        timePullTxt.updateData(list, true)
    }

    private fun addClassPullData(classList: ArrayList<StudentAnswerBean.ClassVo>?) {
        var list = mutableListOf<PullBean>()
        for (classBean in classList!!) {
            var data = PullBean()
            data.searchId = classBean.clssId
            data.content = classBean.className!!
            list.add(data)
        }
        classNamePullTxt.updateData(list, true)
    }

    override fun initListener() {
        backImg.setOnClickListener {
            finish()
        }
        classNamePullTxt.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                mClassId = bean.searchId
                mDataViewModel.getStudentAnswerData(mUserId, mClassId, mTimeId, mWorkId)
            }

        })
        timePullTxt.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                mTimeId = bean.content
                mDataViewModel.getStudentAnswerData(mUserId, mClassId, mTimeId, mWorkId)
            }

        })
        homeWorkPullTxt.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                mWorkId = bean.searchId
                mDataViewModel.getStudentAnswerData(mUserId, mClassId, mTimeId, mWorkId)
            }
        })

        classNamePullTxt.setOnClickListener {
            timePullTxt.hidePop()
            homeWorkPullTxt.hidePop()
            if (classNamePullTxt.isChecked) {
                classNamePullTxt.showPop()
            } else {
                classNamePullTxt.hidePop()
            }

        }
        timePullTxt.setOnClickListener {
            homeWorkPullTxt.hidePop()
            classNamePullTxt.hidePop()
            if (timePullTxt.isChecked) {
                timePullTxt.showPop()
            } else {
                timePullTxt.hidePop()
            }
        }

        homeWorkPullTxt.setOnClickListener {
            timePullTxt.hidePop()
            classNamePullTxt.hidePop()
            if (homeWorkPullTxt.isChecked) {
                homeWorkPullTxt.showPop()
            } else {
                homeWorkPullTxt.hidePop()
            }
        }
    }

}