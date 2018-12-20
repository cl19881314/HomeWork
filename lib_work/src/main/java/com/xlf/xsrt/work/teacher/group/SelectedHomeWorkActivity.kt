package com.xlf.xsrt.work.teacher.group

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import com.xlf.xsrt.work.bean.UserInfo
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.teacher.group.adapter.SelectedHomeworkAdapter
import com.xlf.xsrt.work.teacher.group.viewmodel.SelectedHomeWorkModel
import com.xlf.xsrt.work.widget.CustomDatePicker
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_selected_homework.*
import java.text.SimpleDateFormat
import java.util.*

class SelectedHomeWorkActivity : BaseActivity() {
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(SelectedHomeWorkModel::class.java)
    }
    private val mAdapter by lazy {
        SelectedHomeworkAdapter()
    }
    private val mGroupHomeworkId by lazy {
        intent.getIntExtra("groupHomeworkId", -1)
    }
    private var mCustomDatePicker:CustomDatePicker?=null
    companion object {
        fun start(ctx: Context, groupHomeworkId: Int) {
            val intent = Intent(ctx, SelectedHomeWorkActivity::class.java)
            intent.putExtra("groupHomeworkId", groupHomeworkId)
            ctx.startActivity(intent)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_selected_homework
    }

    override fun init() {
        initRcyView()
        initDatePicker()
        mViewModel.loadSelectHomework(UserInfoConstant.getUserId(), mGroupHomeworkId)
    }

    private fun initDatePicker() {
        var time = ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        val now = dateFormat.format(Date())
        val calendar = Calendar.getInstance()
        calendar.set(2008, 1, 1)
        val start = dateFormat.format(calendar.time)
        toast(start)
        mCustomDatePicker= CustomDatePicker(this, { time ->
            toast(time)
        }, "2010-01-01 00:00", now)
        mCustomDatePicker?.showSpecificTime(true) // 显示时和分
        mCustomDatePicker?.setIsLoop(true) // 允许循环滚动
    }

    private fun initRcyView() {
        rcy_selected_homework.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_selected_homework.adapter = mAdapter
    }


    override fun initListener() {
        titlebar_selected_homework.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })

        btn_subscribe_selected_homework.setOnClickListener { doSubscribeHomeWork() }
        btn_sure_selected_homework.setOnClickListener { doSureHomeWork() }
        grouphomework_name_select.setOnClickListener {
            val editText = EditText(this@SelectedHomeWorkActivity)
            AlertDialog.Builder(this@SelectedHomeWorkActivity)
                    .setTitle("修改名称")
                    .setView(editText)
                    .setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("确定") { dialog, which ->
                        grouphomework_name_select.text = editText.text
                        dialog.dismiss()
                    }
                    .show()


        }
    }

    override fun doResponseData() {
        mViewModel.mGroupHomeworkData.observe(this, Observer {
            grouphomework_name_select.text = it?.homeworkName
            mAdapter.addData(it?.groupedhomeworkList!!, true)
        })
    }

    /**
     * 确认布置
     */
    private fun doSureHomeWork() {
        mViewModel.pushHomeWork(UserInfoConstant.getUserId(), "", mGroupHomeworkId, grouphomework_name_select.text.toString())
    }

    /**
     * 预约布置
     */
    private fun doSubscribeHomeWork() {
        mCustomDatePicker?.show("2018-09-12 12:00")
        mViewModel.pushHomeWork(UserInfoConstant.getUserId(), "", mGroupHomeworkId, grouphomework_name_select.text.toString())
    }
}