package com.xlf.xsrt.work.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.student.model.ReadOvreModel
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.activity_readover.*

class ReadOverActivity : BaseActivity() {
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(ReadOvreModel::class.java)
    }
    private val mStuAnswerId by lazy {
        intent.getIntExtra(STUANSWER_ID, -1)
    }

    companion object {
        private const val STUANSWER_ID = "stuAnsId"
        fun start(ctx: Context, stuAnswerId: Int) {
            val intent = Intent(ctx, ReadOverActivity::class.java)
            intent.putExtra(STUANSWER_ID, stuAnswerId)
            ctx.startActivity(intent)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_readover
    }

    override fun init() {
        mViewModel.getTeacherComment(mStuAnswerId)
    }

    override fun initListener() {
        titlebar_readover.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
    }

    override fun doResponseData() {
        mViewModel.mReadOverData.observe(this, Observer {
            tv_content_readover.text = "${it!!.teacherComment!!.comment}"
            tv_time_readover.text = "${it!!.teacherComment!!.createTime}"
        })
        mViewModel.mReadOverError.observe(this, Observer {
            toast(it!!)
        })
    }
}