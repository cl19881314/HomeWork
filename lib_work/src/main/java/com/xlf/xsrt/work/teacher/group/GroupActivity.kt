package com.xlf.xsrt.work.teacher.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.group.bean.SysDictVo
import com.xlf.xsrt.work.teacher.group.adapter.GroupAdapter
import com.xlf.xsrt.work.teacher.group.adapter.PopWindowAdapter
import com.xlf.xsrt.work.teacher.group.adapter.ScreenPopWindowAdapter
import com.xlf.xsrt.work.teacher.group.bean.QueryCondition
import com.xlf.xsrt.work.teacher.group.viewmodel.GroupModel
import com.xlf.xsrt.work.utils.ScreenUtil
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_group_teacher.*
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_group.view.*
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_screen_group.view.*

class GroupActivity : BaseActivity() {
    private var mPopWindow: PopupWindow? = null
    private var mTextBooks: MutableList<SysDictVo> = mutableListOf()//教材
    private var mDirectors: MutableList<SysDictVo> = mutableListOf() //目录
    private var mSections: MutableList<SysDictVo> = mutableListOf() //章节
    private var mDiffLevels: MutableList<SysDictVo> = mutableListOf() //困难等级

    private val TEXT_BOOK = 0//教材
    private val DIRECTOR = 1//目录
    private val SECTION = 2//章节
    private val SCREEN = 3//筛选

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GroupModel::class.java)
    }
    private val mGroupAdapter by lazy {
        GroupAdapter()
    }
    /**
     * 查询条件封装
     */
    private val mQueryCondition by lazy {
        QueryCondition()
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_group_teacher
    }


    override fun init() {
        rcy_group.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_group.adapter = mGroupAdapter
    }

    private fun showPopWindow(type: Int) {
        when (type) {

            SCREEN -> {
                val windowView = LayoutInflater.from(this).inflate(R.layout.xsrt_layout_popwindow_screen_group, null)
                initWindowView(type, windowView)
                val popWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                popWindow?.isOutsideTouchable = true
                popWindow?.isFocusable = false
                popWindow.animationStyle = R.style.xsrt_popwin_anim_style
                windowView.sure_screen_popwindow.setOnClickListener {
                    popWindow?.dismiss()
                }
                popWindow.setOnDismissListener {
                    screen_group.isChecked = false
                }
                showPopWindow(popWindow, type)
            }
            else -> {
                val windowView = LayoutInflater.from(this).inflate(R.layout.xsrt_layout_popwindow_group, null)
                initWindowView(type, windowView)
                val popWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                popWindow?.isOutsideTouchable = true
                popWindow?.isFocusable = false
                showPopWindow(popWindow, type)
            }
        }
    }

    private fun initWindowView(type: Int, windowView: View) {

        when (type) {
            TEXT_BOOK -> {
                val adapter = PopWindowAdapter()
                windowView.rcy_popwindow.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                windowView.rcy_popwindow.adapter = adapter
                adapter.addData(mTextBooks, true)
            }
            DIRECTOR -> {
                val adapter = PopWindowAdapter()
                windowView.rcy_popwindow.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                windowView.rcy_popwindow.adapter = adapter
                adapter.addData(mDirectors, true)
            }
            SECTION -> {
                val adapter = PopWindowAdapter()
                windowView.rcy_popwindow.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                windowView.rcy_popwindow.adapter = adapter
                adapter.addData(mSections, true)
            }
            SCREEN -> {
                val adapter = ScreenPopWindowAdapter()
                windowView.rcy_screen_popwindow.layoutManager = GridLayoutManager(this, 3)
                windowView.rcy_screen_popwindow.adapter = adapter
                adapter.addData(mDiffLevels, true)
            }
        }
    }

    override fun initListener() {
        textbook_group.setOnClickListener {
            textbook_group.toggle()//只是改变状态
            showPopWindow(TEXT_BOOK)
        }
        director_group.setOnClickListener {
            director_group.toggle()
            showPopWindow(DIRECTOR)
        }
        section_group.setOnClickListener {
            section_group.toggle()
            showPopWindow(SECTION)
        }
        screen_group.setOnClickListener {
            screen_group.toggle()
            showPopWindow(SCREEN)
        }

        titlebar_group.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
    }

    private fun showPopWindow(popWindow: PopupWindow, type: Int) {
        when (type) {
            SCREEN -> {
                popWindow!!.showAtLocation(ll_root_group, Gravity.END, 0, 0)
            }
            else -> {
                if (Build.VERSION.SDK_INT < 24) {
                    popWindow!!.showAsDropDown(divider_group)
                } else {
                    val location = IntArray(2)
                    divider_group.getLocationOnScreen(location)
                    if (Build.VERSION.SDK_INT == 25) {
                        var tempheight = popWindow!!.height
                        if (tempheight == WindowManager.LayoutParams.MATCH_PARENT || ScreenUtil.getScreenHeight(this) <= tempheight) {
                            popWindow!!.height = ScreenUtil.getScreenHeight(this) - location[1] - divider_group.height;
                        }
                    } else {
                        val visibleFrame = Rect()
                        divider_group.getGlobalVisibleRect(visibleFrame)
                        val height = divider_group.resources.displayMetrics.heightPixels - visibleFrame.bottom
                        popWindow!!.height = height
                    }
                    popWindow!!.showAsDropDown(divider_group, location[0], location[1] + divider_group.height)
                }
            }
        }

    }


    override fun doResponseData() {
        mViewModel.mGroupData.observe(this, Observer {
            if (it?.flag == RESPONSE_SUCCESS) {
                mGroupAdapter.addData(it.homeworkBaseList!!)
            }
        })
        mViewModel.mHomeworkData.observe(this, Observer {
            if (mQueryCondition.page == 0) {
                if (it == null || it.size == 0) {
                    showEmptyView()
                } else {
                    mGroupAdapter.addData(it, true)
                }
            } else {
                if (it == null || it.size == 0) {
                    toast("没有更多了")
                } else {
                    mGroupAdapter.addData(it)
                }
            }
        })
    }

    /**
     * Todo：空数据页面
     */
    private fun showEmptyView() {

    }


}