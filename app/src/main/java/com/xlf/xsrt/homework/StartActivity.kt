package com.xlf.xsrt.homework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xlf.xsrt.work.utils.LoginUtil
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        btn_teacher.setOnClickListener {
            //            var intent = Intent(this, TeacherActivity::class.java)
//            startActivity(intent)
            LoginUtil.enterHomeWork(this, "token2")
        }
        btn_student.setOnClickListener {
            //            var intent = Intent(this, StudentActivity::class.java)
//            startActivity(intent)
            LoginUtil.enterHomeWork(this, "token1")
        }
        logonBtn.setOnClickListener {
            var user = userEdt.text.toString()
            var pwd = pwdEdt.text.toString()
            LoginUtil.login(this@StartActivity, user, pwd)
        }
    }

}
