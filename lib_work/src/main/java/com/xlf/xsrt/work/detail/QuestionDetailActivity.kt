package com.xlf.xsrt.work.detail

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.WriteCommentActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import kotlinx.android.synthetic.main.xsrt_activity_answer_detail_layout.*

/**
 * 题目详情界面 可以左右滑动
 */
class QuestionDetailActivity : BaseActivity() {
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

        var title = intent.getStringExtra("title")
        titleBar.setTitleTxt(title)
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
    }

    override fun initListener() {
        toDoCommentTxt.setOnClickListener {
            var intent = Intent(this@QuestionDetailActivity, WriteCommentActivity::class.java)
            intent.putExtra("stuAnswerId", mStuAnswerId)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            finish()
        }
    }

}