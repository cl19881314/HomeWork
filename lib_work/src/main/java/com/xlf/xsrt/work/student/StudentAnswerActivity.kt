package com.xlf.xsrt.work.student

import android.support.v4.app.Fragment
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import kotlinx.android.synthetic.main.xsrt_activity_student_choose_answer.*

/**
 * @author Chenhong
 * @date 2018/12/17.
 * @des
 */
class StudentAnswerActivity : BaseActivity(){
    private var mAdapter: StudentAnswerDetailAdapter? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student_choose_answer
    }

    override fun init() {
        mAdapter =  StudentAnswerDetailAdapter()
        answerVp.adapter = mAdapter
        getDetailData()
    }

    private fun getDetailData() {
        var data = ArrayList<Fragment>()
        for (i in 0..10) {
            var fragment = StudentAnswerDetailFragment()
            data.add(fragment)
        }
        mAdapter?.setData(data)
    }

}