package com.xlf.xsrt.work.teacher.answer

import android.arch.lifecycle.ViewModelProviders
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.viewmodel.StuAnswerDetailViewModel
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_teacher_coment_layout.*

class WriteCommentActivity : BaseActivity(){
    private val mDataViewModel by lazy {
        ViewModelProviders.of(this).get(StuAnswerDetailViewModel::class.java)
    }
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_write_comment_layout
    }

    override fun init() {
        titleBar.setTitleBarClickListener(object : TitleBar.TitleBarClickListener{
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
                addComment()
            }

        })
    }

    fun addComment(){

    }

}