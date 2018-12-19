package com.xlf.xsrt.work.teacher.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import com.xlf.xsrt.work.bean.SysDictVo
import com.xlf.xsrt.work.teacher.group.adapter.GroupAdapter
import com.xlf.xsrt.work.teacher.group.adapter.PopWindowAdapter
import com.xlf.xsrt.work.teacher.group.adapter.ScreenPopWindowAdapter
import com.xlf.xsrt.work.bean.QueryCondition
import com.xlf.xsrt.work.teacher.group.viewmodel.GroupModel
import com.xlf.xsrt.work.utils.ScreenUtil
import com.xlf.xsrt.work.widget.TitleBar
import com.xlf.xsrt.work.widget.pulltextview.PullBean
import com.xlf.xsrt.work.widget.pulltextview.PullTextView
import kotlinx.android.synthetic.main.xsrt_activity_group_teacher.*
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_group.view.*
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_screen_group.view.*

class GroupActivity : BaseActivity() {

    private var mDiffPopWindow: PopupWindow? = null
    private var mDiffAdapter: ScreenPopWindowAdapter? = null

    private var mTextBooks: MutableList<PullBean> = mutableListOf()//教材
    private var mDirectors: MutableList<PullBean> = mutableListOf() //目录
    private var mSections: MutableList<PullBean> = mutableListOf() //章节
    private var mDiffLevels: MutableList<SysDictVo> = mutableListOf() //困难等级

    private var mSelectedHomeWorks: ArrayList<HomeworkBaseVo> = ArrayList() //选择预添加的作业

    private val mTeacherId by lazy {
        intent.getIntExtra("teacherId", -1)
    }

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

    companion object {
        fun start(ctx: Context, teacherId: Int) {
            val intent = Intent(ctx, GroupActivity::class.java)
            intent.putExtra("teacherId", teacherId)
            ctx.startActivity(intent)
        }
    }


    override fun init() {
        initRcyView()
        initDiffPopWindow()
        initData()
    }

    private fun initData() {
        mViewModel.loadGroupData(mTeacherId)//初始获取组作业数据
    }

    private fun initRcyView() {
        rcy_group.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_group.adapter = mGroupAdapter
    }

    private fun initDiffPopWindow() {
        val windowView = LayoutInflater.from(this).inflate(R.layout.xsrt_layout_popwindow_screen_group, null)
        mDiffAdapter = ScreenPopWindowAdapter()
        windowView.rcy_screen_popwindow.layoutManager = GridLayoutManager(this, 3)
        windowView.rcy_screen_popwindow.adapter = mDiffAdapter
        mDiffPopWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mDiffPopWindow?.isOutsideTouchable = true
        mDiffPopWindow?.isFocusable = false
        mDiffPopWindow?.animationStyle = R.style.xsrt_popwin_anim_style
        windowView.sure_screen_popwindow.setOnClickListener {
            mDiffPopWindow?.dismiss()
        }
        windowView.screen_root_pop_group.setOnClickListener {
            mDiffPopWindow?.dismiss()
        }
    }

    override fun initListener() {
        titlebar_group.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })
        textbook_group.setOnClickListener {
            if (textbook_group.isChecked) {
                textbook_group.showPop()
                if (director_group.isPopWindowShow()) {
                    director_group.hidePop()
                }
                if (section_group.isPopWindowShow()) {
                    section_group.hidePop()
                }
            } else {
                textbook_group.hidePop()
            }
        }
        director_group.setOnClickListener {
            if (director_group.isChecked) {
                director_group.showPop()
                if (textbook_group.isPopWindowShow()) {
                    textbook_group.hidePop()
                }
                if (section_group.isPopWindowShow()) {
                    section_group.hidePop()
                }
            } else {
                director_group.hidePop()
            }
        }
        section_group.setOnClickListener {
            if (section_group.isChecked) {
                section_group.showPop()
                if (textbook_group.isPopWindowShow()) {
                    textbook_group.hidePop()
                }
                if (director_group.isPopWindowShow()) {
                    director_group.hidePop()
                }
            } else {
                section_group.hidePop()
            }
        }
        screen_group.setOnClickListener {
            if (textbook_group.isPopWindowShow()) {
                textbook_group.hidePop()
            }
            if (director_group.isPopWindowShow()) {
                director_group.hidePop()
            }
            if (section_group.isPopWindowShow()) {
                section_group.hidePop()
            }
            mDiffAdapter?.addData(mDiffLevels, true)
            mDiffPopWindow!!.showAtLocation(ll_root_group, Gravity.END, 0, 0)
        }

        //全选
        selectedAll_group.setOnClickListener {
            val data = mGroupAdapter.getData()
            if (selectedAll_group.isChecked) {
                for (i in 0 until data.size) {
                    data[i].addFlag = 1
                }
            } else {
                for (i in 0 until data.size) {
                    data[i].addFlag = 0
                }
            }
            mGroupAdapter.notifyDataSetChanged()
        }
        //已选作业
        selectedNum_group.setOnClickListener {
            SelectedHomeWorkActivity.start(this, mSelectedHomeWorks)
        }
    }


    override fun doResponseData() {
        mViewModel.mGroupData.observe(this, Observer {
            if (it?.flag == RESPONSE_SUCCESS) {
                if (it.homeworkBaseList!!.size > 0) {
                    mGroupAdapter.addData(it.homeworkBaseList!!)
                } else {
                    showEmptyView()
                }
                //重置困难等级
                mDiffLevels.clear()
                mDiffLevels.addAll(it.difficultyList!!)
                mDiffAdapter?.addData(mDiffLevels, true)
                //重置教材数据
                mTextBooks.clear()
                for (i in 0 until it.textBookList!!.size) {
                    val pullBean = PullBean()
                    pullBean.content = it.textBookList!![i].sysDictName!!
                    pullBean.searchId = it.textBookList!![i].sysDictId!!
                }
                textbook_group.updateData(mTextBooks, true)
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