package com.xlf.xsrt.work.teacher.answer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerAdapter
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.teacher.answer.viewmodel.StudentAnswerViewModel
import com.xlf.xsrt.work.utils.DateUtil
import com.xlf.xsrt.work.widget.pulltextview.PullBean
import com.xlf.xsrt.work.widget.pulltextview.PullTextView
import kotlinx.android.synthetic.main.xsrt_activity_student_answer_layout.*
import kotlinx.android.synthetic.main.xsrt_item_empty_layout.*

/**
 * 学生作业
 */
class StudentAnswerActivity : BaseActivity() {
    private var mAdapter: StudentAnswerAdapter? = null
    private var mClassId = -1
    private var mTimeId = ""
    private var mWorkId = -1
    private var mChooseType = -1 //用于判断选择哪个筛选条件，控制是否刷新瞎选条目
    private val mDataViewModel by lazy {
        ViewModelProviders.of(this@StudentAnswerActivity).get(StudentAnswerViewModel::class.java)
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student_answer_layout
    }

    override fun init() {
        mDataViewModel.getStudentAnswerData(UserInfoConstant.getUserId(), -1, "", -1)
//        mDataViewModel.getStudentAnswerData(1, -1, "", -1)
        mAdapter = StudentAnswerAdapter()
        showDataRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        showDataRv.adapter = mAdapter
        showDataRv.isLoadable = false
    }

    override fun doResponseData() {
        mDataViewModel.mAnswerViewModel.observe(this, Observer {
            if (it?.flag == 1) {
                if (it?.stuAnswerList?.size ?: -1 > 0) {
                    showDataRv.visibility = View.VISIBLE
                    emptyLayout.visibility = View.GONE
                    showDataRv.scrollToPosition(0)
                    mAdapter!!.setAnswerData(it?.stuAnswerList)
                } else {
                    emptyLayout.visibility = View.VISIBLE
                    showDataRv.visibility = View.GONE
                }
                if (it?.classList?.size ?: -1 > 0 && mChooseType == -1) {
                    addClassPullData(it?.classList!!)
                }
                if (it?.createTimeList?.size ?: -1 > 0 && (mChooseType == 0 || mChooseType == -1)) {
                    addTimePullData(it?.createTimeList!!)
                }
                if (it?.homeworkList?.size ?: -1 > 0 && mChooseType != 2) {
                    addWorkPullData(it?.homeworkList!!)
                }
            }
        })
        mDataViewModel.mErrorData.observe(this, Observer {
            toast(it!!)
        })
    }

    private fun addWorkPullData(homeworkList: ArrayList<StudentAnswerBean.HomeworkVo>) {
        homeWorkPullTxt.text = homeworkList[0].homeworkName
        var list = mutableListOf<PullBean>()
        for (i in homeworkList!!.indices) {
            var data = PullBean()
            data.searchId = homeworkList!![i].homeworkId
            data.content = homeworkList!![i].homeworkName
            if (i == 0) {
                data.selected = true
            }
            list.add(data)
        }
        homeWorkPullTxt.updateData(list, true)
    }

    private fun addTimePullData(timeList: ArrayList<StudentAnswerBean.ClassVo>) {
        timePullTxt.text = timeList[0].createTime
        var list = mutableListOf<PullBean>()
        for (i in timeList!!.indices) {
            var data = PullBean()
            data.searchId = i
            data.content = timeList[i].createTime
            if (i == 0) {
                data.selected = true
            }
            list.add(data)
        }
        timePullTxt.updateData(list, true)
    }

    private fun addClassPullData(classList: ArrayList<StudentAnswerBean.ClassVo>) {
        classNamePullTxt.text = classList[0].className
        var list = mutableListOf<PullBean>()
        for (i in classList!!.indices) {
            var data = PullBean()
            data.searchId = classList!![i].classId
            data.content = classList!![i].className
            if (i == 0) {
                data.selected = true
            }
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
                mChooseType = 0
                mClassId = bean.searchId
                mDataViewModel.getStudentAnswerData(UserInfoConstant.getUserId(), mClassId, "", -1)
            }

        })
        timePullTxt.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                mTimeId = DateUtil.chainToString2(bean.content)
                mChooseType = 1
                mDataViewModel.getStudentAnswerData(UserInfoConstant.getUserId(), mClassId, mTimeId, -1)
            }

        })
        homeWorkPullTxt.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                mWorkId = bean.searchId
                mChooseType = 2
                mDataViewModel.getStudentAnswerData(UserInfoConstant.getUserId(), mClassId, mTimeId, mWorkId)
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