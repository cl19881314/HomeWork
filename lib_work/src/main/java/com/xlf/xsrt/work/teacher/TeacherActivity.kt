package com.xlf.xsrt.work.teacher

import android.content.Context
import android.content.Intent
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.answer.StudentAnswerActivity
import com.xlf.xsrt.work.teacher.group.GroupActivity
import com.xlf.xsrt.work.teacher.mylayout.MyArrangeActivity
import kotlinx.android.synthetic.main.xsrt_activity_teacher_main.*

/**
 * 教师端主界面
 */
class TeacherActivity : BaseActivity() {


    companion object {
        fun start(ctx: Context) {
            val intent = Intent(ctx, TeacherActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_teacher_main
    }

    override fun init() {
        backImg.setOnClickListener {
            finish()
        }
        groupHomeWorkLL.setOnClickListener {
            GroupActivity.start(this)
        }

        myArrangeLL.setOnClickListener {
            var intent = Intent(this@TeacherActivity, MyArrangeActivity::class.java)
            startActivity(intent)
        }
        studentAnswerLl.setOnClickListener {
            var intent = Intent(this@TeacherActivity, StudentAnswerActivity::class.java)
            startActivity(intent)
        }
    }

}