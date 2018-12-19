package com.xlf.xsrt.work.teacher.group

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import com.xlf.xsrt.work.teacher.group.adapter.SelectedHomeworkAdapter
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_selected_homework.*

class SelectedHomeWorkActivity : BaseActivity() {
    private val mData by lazy {
        intent.getParcelableArrayListExtra<HomeworkBaseVo>("data")
    }

    private val mAdapter by lazy {
        SelectedHomeworkAdapter()
    }

    companion object {
        fun start(ctx: Context, data: ArrayList<HomeworkBaseVo>) {
            val intent = Intent(ctx, SelectedHomeWorkActivity::class.java)
            intent.putExtra("data", data)
            ctx.startActivity(intent)

        }
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_selected_homework
    }

    override fun init() {
        rcy_selected_homework.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_selected_homework.adapter = mAdapter
        mAdapter.addData(mData, true)
    }

    override fun initListener() {
        titlebar_selected_homework.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })

        btn_subscribe_selected_homework.setOnClickListener { doSubscribeHomeWork() }
        btn_sure_selected_homework.setOnClickListener { doSureHomeWork() }
    }

    /**
     * 确认布置
     */
    private fun doSureHomeWork() {

    }

    /**
     * 预约布置
     */
    private fun doSubscribeHomeWork() {

    }
}