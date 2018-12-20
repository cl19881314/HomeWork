package com.xlf.xsrt.work.student

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import kotlinx.android.synthetic.main.xsrt_activity_student_choose_answer.*
import kotlinx.android.synthetic.main.xsrt_item_answer_option.view.*
import java.util.*

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
        answerVp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                initOption(position)
            }

        })
    }

    private fun getDetailData() {
        var data = ArrayList<Fragment>()
        for (i in 0..10) {
            var fragment = StudentAnswerDetailFragment()
            data.add(fragment)
        }
        mAdapter?.setData(data)
        initOption(4)
    }

    private fun initOption(size : Int){
        if(size <= 0){
            return
        }
        chooseAnswerLL.removeAllViews()
        for (i in 0 until size) {
            var optionView = LayoutInflater.from(this).inflate(R.layout.xsrt_item_answer_option, null, false)
            var txt = (i + 65).toChar()
            optionView.optionTxt.text = txt.toString()
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            optionView.layoutParams = params
            optionView.optionTxt.setOnClickListener {

            }
            chooseAnswerLL.addView(optionView)
        }
    }

}