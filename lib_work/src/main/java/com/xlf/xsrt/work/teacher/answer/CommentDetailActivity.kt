package com.xlf.xsrt.work.teacher.answer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.viewmodel.TeacherCommentViewModel
import kotlinx.android.synthetic.main.xsrt_activity_teacher_coment_layout.*

/**
 * 教师端 查看老师的批阅
 */
class CommentDetailActivity : BaseActivity(){

    private val mDataViewModel by lazy {
        ViewModelProviders.of(this).get(TeacherCommentViewModel::class.java)
    }
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_teacher_coment_layout
    }

    override fun init() {
        mDataViewModel.getTeacherCommentData(intent.getIntExtra("stuAnswerId", -1))
        mDataViewModel.mCommentViewModel.observe(this, Observer {
            if (it?.flag == 1){
                timeTxt.text = it.teacherComment?.createTime
                commentTxt.text = it.teacherComment?.comment
            }
        })

        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
    }

}