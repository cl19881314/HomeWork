package com.xlf.xsrt.work.teacher.answer

import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import kotlinx.android.synthetic.main.activity_teacher_coment_layout.*

class CommentDetailActivity : BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.activity_teacher_coment_layout
    }

    override fun init() {
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
    }

}