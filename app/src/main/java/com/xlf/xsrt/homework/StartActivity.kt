package com.xlf.xsrt.homework

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xlf.xsrt.work.teacherclient.GroupActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun logonClick(view : View){
        var  intent = Intent(this, GroupActivity::class.java)
        startActivity(intent)
    }
}
