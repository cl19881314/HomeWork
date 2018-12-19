package com.xlf.xsrt.work.teacher.group

import android.content.Context
import android.content.Intent
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_selected_homework.*

class SelectedHomeWorkActivity : BaseActivity() {

    companion object {
        fun start(ctx: Context) {
            val intent = Intent(ctx, SelectedHomeWorkActivity::class.java)
            ctx.startActivity(intent)

        }
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_selected_homework
    }

    override fun init() {

    }

    override fun initListener() {
        titlebar_selected_homework.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
    }
}