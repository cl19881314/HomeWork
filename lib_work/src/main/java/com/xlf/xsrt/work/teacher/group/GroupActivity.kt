package com.xlf.xsrt.work.teacher.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.entry.HomeworkBaseVo
import com.xlf.xsrt.work.teacher.group.adapter.GroupAdapter
import com.xlf.xsrt.work.teacher.group.viewmodel.GroupModel
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.activity_group_teacher.*

class GroupActivity : BaseActivity() {
    private var mPopWindow: PopupWindow? = null

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GroupModel::class.java)
    }
    private val mGroupAdapter by lazy {
        GroupAdapter()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_group_teacher
    }

    override fun init() {
        rcy_group.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_group.adapter = mGroupAdapter
        initPopWindow()
    }

    private fun initPopWindow() {
        val windowView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_group, null)
        mPopWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopWindow?.isOutsideTouchable = true
    }

    override fun initListener() {
        textbook_group.setOnClickListener {
            textbook_group.toggle()
            if (textbook_group.isChecked) {
                showPopWindow()
            } else {
                hidePopWindow()
            }
        }
        director_group.setOnClickListener {
            director_group.toggle()
            if (director_group.isChecked) {
                showPopWindow()
            } else {
                hidePopWindow()
            }
        }
        section_group.setOnClickListener {
            section_group.toggle()
            if (section_group.isChecked) {
                showPopWindow()
            } else {
                hidePopWindow()
            }
        }
        titlebar_group.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
    }

    private fun hidePopWindow() {
        if (mPopWindow!!.isShowing) {
            mPopWindow!!.dismiss()
        }
    }

    private fun showPopWindow() {
        mPopWindow?.showAsDropDown(divider_group)
    }

    override fun doResponseData() {
        mViewModel.mGroupData.observe(this, Observer {
            if (it?.flag == RESPONSE_SUCCESS) {
                mGroupAdapter.addData(it.homeworkBaseList!!)
            }
        })
    }


}