package com.xlf.xsrt.work.detail

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.teacher.answer.WriteCommentActivity
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_answer_detail_layout.*

/**
 * 题目详情界面 可以左右滑动
 */
class QuestionDetailActivity : BaseActivity() {
    private var mAdapter: StudentAnswerDetailAdapter? = null
    private var mStuAnswerId = -1
    private var mUrlList: ArrayList<String>? = null
    private var mTitle = ""
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_answer_detail_layout
    }

    override fun init() {
        mStuAnswerId = intent.getIntExtra("stuAnswerId", -1)
        mUrlList = intent.getStringArrayListExtra("urlList")
        var showTodo = intent.getBooleanExtra("showTodo", false)
        toDoCommentTxt.visibility = if (showTodo) View.VISIBLE else View.GONE

        mTitle = intent.getStringExtra("title")
        if (!TextUtils.isEmpty(mTitle)) {
            titleBar_answer_detail.setTitleTxt(mTitle)
        } else {
            titleBar_answer_detail.setTitleTxt("1/${mUrlList?.size}")
            detailViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    titleBar_answer_detail.setTitleTxt("${position + 1 }/${mUrlList?.size}")
                }

            })
        }
        mAdapter = StudentAnswerDetailAdapter(supportFragmentManager)
        detailViewPager.adapter = mAdapter
        getDetailData()
    }


    private fun getDetailData() {
        if (mUrlList == null || mUrlList!!.isEmpty()) {
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
            intent.putExtra("title", mTitle)
            startActivityForResult(intent, 100)
        }
        titleBar_answer_detail.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //发布批阅后回到学生作业界面
            finish()
        }
    }

}