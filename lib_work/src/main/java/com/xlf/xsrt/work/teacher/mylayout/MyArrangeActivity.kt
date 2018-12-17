package com.xlf.xsrt.work.teacher.mylayout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.mylayout.adapter.MyArrangeAdapter
import kotlinx.android.synthetic.main.xsrt_activity_my_arrange_layout.*

/**
 * 我的布置
 */
class MyArrangeActivity : BaseActivity() {
    private var mAdapter: MyArrangeAdapter? = null
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_my_arrange_layout
    }

    override fun init() {
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }

        mAdapter = MyArrangeAdapter()
        showDataRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        showDataRv.adapter = mAdapter

        showDataRv.setOnLoadMoreListener {

        }
        delTxt.setOnClickListener {

        }
    }

}