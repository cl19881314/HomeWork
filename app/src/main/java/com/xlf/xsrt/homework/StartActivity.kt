package com.xlf.xsrt.homework

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xlf.xsrt.work.MainActivity

class StartActivity : AppCompatActivity() {
    /**
     *dddfdsa
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun logonClick(view : View){
        var  intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
