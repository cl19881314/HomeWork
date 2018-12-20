package com.xlf.xsrt.work.student

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import kotlinx.android.synthetic.main.xsrt_activity_student_choose_answer.*
import kotlinx.android.synthetic.main.xsrt_item_answer_option.view.*

/**
 * @author Chenhong
 * @date 2018/12/17.
 * @des 学生答题界面
 */
class StudentToAnswerActivity : BaseActivity(){
    private var mAdapter: StudentAnswerDetailAdapter? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student_choose_answer
    }

    override fun init() {
        mAdapter =  StudentAnswerDetailAdapter(supportFragmentManager)
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

        for (i in 0..4) {
            var optionView = LayoutInflater.from(this).inflate(R.layout.xsrt_item_answer_option, null, false)
            var txt = (i + 65).toChar()
            optionView.optionTxt.text = txt.toString()
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            optionView.layoutParams = params
            optionView.optionTxt.setOnClickListener {

            }
            if (i == 1){
                optionView.optionTxt.setBackgroundResource(R.drawable.xsrt_answer_choosed)
                optionView.optionTxt.setTextColor(resources.getColor(R.color.xsrt_white))
            } else {
                optionView.optionTxt.setBackgroundResource(R.drawable.xsrt_answer_normal)
                optionView.optionTxt.setTextColor(resources.getColor(R.color.xsrt_btn_bg_color))
            }
            chooseAnswerLL.addView(optionView)
        }
    }

}