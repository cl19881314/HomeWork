package com.xlf.xsrt.work.teacher

import android.content.Intent
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.group.GroupActivity
import com.xlf.xsrt.work.teacher.mylayout.MyArrangeActivity
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.activity_main.*

class TeacherActivity : BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
        groupHomeWorkLL.setOnClickListener {
            var intent = Intent(this@TeacherActivity, GroupActivity::class.java)
            startActivity(intent)
        }

        myArrangeLL.setOnClickListener {
            var intent = Intent(this@TeacherActivity, MyArrangeActivity::class.java)
            startActivity(intent)
        }
        studentWork.setOnClickListener {


        }
    }

}