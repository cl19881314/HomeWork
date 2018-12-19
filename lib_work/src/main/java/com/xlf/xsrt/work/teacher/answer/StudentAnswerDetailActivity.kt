package com.xlf.xsrt.work.teacher.answer

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import kotlinx.android.synthetic.main.xsrt_activity_answer_detail_layout.*
import kotlinx.android.synthetic.main.xsrt_item_answer_option.view.*

class StudentAnswerDetailActivity : BaseActivity() {
    private var mAdapter: StudentAnswerDetailAdapter? = null
    private var mStuAnswerId = -1
    private var mUrlList: ArrayList<String>? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_answer_detail_layout
    }

    override fun init() {
        mStuAnswerId = intent.getIntExtra("stuAnswerId", -1)
        mUrlList = intent.getStringArrayListExtra("urlList")
        var showTodo = intent.getBooleanExtra("showTodo", false)
        toDoCommentTxt.visibility = if (showTodo) View.VISIBLE else View.GONE

        var stuName = intent.getStringExtra("stuName")
        titleBar.setTitleTxt(stuName)
        mAdapter = StudentAnswerDetailAdapter(supportFragmentManager)
        detailViewPager.adapter = mAdapter
        getDetailData()


    }

    private fun getDetailData() {
        if (mUrlList == null || mUrlList!!.isEmpty()){
            return
        }
        var data = ArrayList<Fragment>()
        for (i in mUrlList!!.indices) {
            var fragment = StudentAnswerDetailFragment()
            var bundle = Bundle()
            bundle.putString("url", mUrlList!![i])
            fragment.arguments = bundle
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

    override fun initListener() {
        toDoCommentTxt.setOnClickListener {
            var intent = Intent(this@StudentAnswerDetailActivity, WriteCommentActivity::class.java)
            intent.putExtra("stuAnswerId", mStuAnswerId)
            startActivity(intent)
        }
    }

}