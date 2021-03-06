package com.xlf.xsrt.work.teacher.group

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.http.RequestApi
import com.xlf.xsrt.work.bean.SysDictVo
import com.xlf.xsrt.work.teacher.group.adapter.GroupAdapter
import com.xlf.xsrt.work.teacher.group.adapter.ScreenPopWindowAdapter
import com.xlf.xsrt.work.bean.QueryCondition
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.detail.SubjectDetailActivity
import com.xlf.xsrt.work.eventbus.RefreshEvent
import com.xlf.xsrt.work.teacher.group.bean.AddRespondeBean
import com.xlf.xsrt.work.teacher.group.viewmodel.GroupModel
import com.xlf.xsrt.work.widget.TitleBar
import com.xlf.xsrt.work.widget.pulltextview.PullBean
import com.xlf.xsrt.work.widget.pulltextview.PullTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.xsrt_activity_group_teacher.*
import kotlinx.android.synthetic.main.xsrt_item_empty_layout.view.*
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_screen_group.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GroupActivity : BaseActivity() {

    private var mDiffPopWindow: PopupWindow? = null
    private var mDiffAdapter: ScreenPopWindowAdapter? = null
    private var isFirstInitData = false //处理由于webview太长，导致scrollToTop不能显示完全的bug

    private var mTextBooks: MutableList<SysDictVo> = mutableListOf()//教材
    private var mDirectors: MutableList<SysDictVo> = mutableListOf() //目录
    private var mSections: MutableList<SysDictVo> = mutableListOf() //章节
    private var mDiffLevels: MutableList<SysDictVo> = mutableListOf() //困难等级

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
        fun start(ctx: Context) {
            val intent = Intent(ctx, GroupActivity::class.java)
            ctx.startActivity(intent)
        }
    }


    override fun init() {
        initRcyView()
        initDiffPopWindow()
        initData()
        EventBus.getDefault().register(this)
    }

    private fun initData() {
        mViewModel.loadGroupData(UserInfoConstant.getUserId())//初始获取组作业数据
        mQueryCondition.userId = UserInfoConstant.getUserId().toString()
    }

    private var manager: LinearLayoutManager? = null
    private fun initRcyView() {
        screen_group.paint.isFakeBoldText = true//加粗
        manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_group.layoutManager = manager
        rcy_group.adapter = mGroupAdapter
        rcy_group.setOnLoadMoreListener {
            if (manager!!.findLastCompletelyVisibleItemPosition() == mGroupAdapter.getData().size + 1)
                mViewModel.queryHomeworkData(mQueryCondition)
            else {
                rcy_group.stopLoadMore()
            }
        }
    }

    private var posSelected: Int = -1
    private fun initDiffPopWindow() {
        val windowView = LayoutInflater.from(this).inflate(R.layout.xsrt_layout_popwindow_screen_group, null)
        mDiffAdapter = ScreenPopWindowAdapter()
        windowView.rcy_screen_popwindow.layoutManager = GridLayoutManager(this, 3)
        windowView.rcy_screen_popwindow.adapter = mDiffAdapter
        mDiffPopWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mDiffPopWindow?.isOutsideTouchable = true
        mDiffPopWindow?.isFocusable = false
        mDiffPopWindow?.animationStyle = R.style.xsrt_popwin_anim_style
        //初始化
        windowView.rbtn_base_screen.isChecked = mQueryCondition.baseFlag == "1"
        windowView.rbtn_collect_screen.isChecked = mQueryCondition.baseFlag == "0"
        if (posSelected != -1 && TextUtils.isEmpty(mQueryCondition.difficultyId.trim())) {
            mDiffAdapter?.setIitemChecked(posSelected)
        } else {
            mDiffAdapter?.unCheckedAll()
        }
        mDiffAdapter?.setOnItemClickListener(object : BaseRcyAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                mDiffAdapter?.setIitemChecked(position)
            }

        })
        //清除选项
        windowView.clear_screen_popwindow.setOnClickListener {
            windowView.rbtn_base_screen.isChecked = true
            mDiffAdapter?.unCheckedAll()
        }
        windowView.sure_screen_popwindow.setOnClickListener {
            mDiffPopWindow?.dismiss()
            //点击确定，开始筛选搜索
            mQueryCondition.page = 0
            if (windowView.rbtn_collect_screen.isChecked) {
                mQueryCondition.baseFlag = "0"
            } else {
                mQueryCondition.baseFlag = "1"
            }
            posSelected = mDiffAdapter!!.posSelected
            if (posSelected == -1) {
                mQueryCondition.difficultyId = ""
            } else {
                mQueryCondition.difficultyId = mDiffAdapter!!.getData()[posSelected].sysDictId!!.toString()
            }
            mViewModel.queryHomeworkData(mQueryCondition)
        }
        windowView.screen_root_pop_group.setOnClickListener {
            mDiffPopWindow?.dismiss()
        }

        mDiffPopWindow?.setOnDismissListener {

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
            val contentView = mDiffPopWindow?.contentView
            contentView?.rbtn_base_screen?.isChecked = mQueryCondition.baseFlag == "1"
            contentView?.rbtn_collect_screen?.isChecked = mQueryCondition.baseFlag == "0"
            if (posSelected != -1 && !TextUtils.isEmpty(mQueryCondition.difficultyId.trim())) {
                mDiffAdapter?.setIitemChecked(posSelected)
            } else {
                mDiffAdapter?.unCheckedAll()
            }
            mDiffPopWindow!!.showAtLocation(ll_root_group, Gravity.END, 0, 0)
        }

        textbook_group.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                //重置目录数据
                resetDirectorData(position)
                //重置章节数据，默认为第一条目录数据
                setDefaultSectionData()
                //加入教材筛选条件
                mQueryCondition.textbookId = bean.searchId.toString()
                //查询数据 更新界面
                mQueryCondition.page = 0
                mViewModel.queryHomeworkData(mQueryCondition)
            }

        })
        director_group.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                //重置章节数据
                resetSectionData(position)
                //加入目录筛选条件
                mQueryCondition.directoryId = bean.searchId.toString()
                //查询数据 更新界面
                mQueryCondition.page = 0
                mViewModel.queryHomeworkData(mQueryCondition)
            }

        })
        section_group.setItemClickListener(object : PullTextView.PullListItemListener {
            override fun onItemClick(bean: PullBean, position: Int) {
                //加入章节筛选条件
                mQueryCondition.chapterId = bean.searchId.toString()
                //查询数据 更新界面
                mQueryCondition.page = 0
                mViewModel.queryHomeworkData(mQueryCondition)
            }
        })

        mGroupAdapter.setOnItemChildViewClickListener(object : BaseRcyAdapter.ItemChildViewClickListener {
            override fun onItemChildClick(childView: View, position: Int) {
                val bean = mGroupAdapter.getData()[position]
                when (childView.id) {
                    R.id.isAdded -> {
                        val checkBox = childView as CheckBox
                        if (checkBox.isChecked) {
                            mViewModel.addOrCancleHomework(UserInfoConstant.getUserId()
                                    , 1, mQueryCondition.groupedHomeworkId, bean.homeworkId.toString()
                                    , object : GroupModel.ResultListener<AddRespondeBean> {
                                override fun onSuccess(t: AddRespondeBean) {
                                    mViewModel.mSelectedNum.value = t.groupedCount
                                    bean.addFlag = 1
                                    toast("添加成功")
                                }

                                override fun onFail(t: String) {
                                    checkBox.isChecked = false
                                    toast("添加失败")
                                }
                            })


                        } else {
                            //移除
                            mViewModel.addOrCancleHomework(UserInfoConstant.getUserId()
                                    , 0, mQueryCondition.groupedHomeworkId, bean.homeworkId.toString()
                                    , object : GroupModel.ResultListener<AddRespondeBean> {
                                override fun onSuccess(t: AddRespondeBean) {
                                    mViewModel.mSelectedNum.value = t.groupedCount
                                    bean.addFlag = 0
                                    toast("移除成功")
                                }

                                override fun onFail(t: String) {
                                    checkBox.isChecked = true
                                    toast("移除失败")
                                }
                            })
                        }
                    }
                    R.id.isCollocted -> {
                        //收藏
                        val imgview = childView as ImageView
                        RequestApi.getInstance().collectOrCancel(UserInfoConstant.getUserId(), bean.homeworkId, bean.collectFlag)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    when (it.flag) {
                                        0 -> toast(it.msg!!)
                                        1 -> {
                                            bean.collectFlag = Math.abs(bean.collectFlag!! - 1)
                                            if (bean.collectFlag == 1) {
                                                imgview.setBackgroundResource(R.drawable.xsrt_ic_collection_yes)
                                                toast("收藏成功")
                                            } else {
                                                imgview.setBackgroundResource(R.drawable.xsrt_ic_collection_no)
                                                if (mQueryCondition.baseFlag == "0") {
                                                    mGroupAdapter.removeData(position, true)
                                                }
                                                toast("取消收藏成功")
                                            }
                                        }
                                    }
                                }, { e ->
                                    toast(if (bean.collectFlag == 0) {
                                        "收藏失败"
                                    } else {
                                        "取消收藏失败"
                                    })
                                })

                    }
                    R.id.fl_subject_item -> {
                        //详情
                        var intent = Intent(this@GroupActivity, SubjectDetailActivity::class.java)
                        intent.putExtra("url", bean.homeworkDetailUrl)
                        intent.putExtra("num", bean.itemCode.toString())
                        startActivity(intent)
                    }
                    else -> {
                    }
                }
            }

        })
        select_num_group.setOnClickListener {
            SelectedHomeWorkActivity.start(this@GroupActivity, mQueryCondition.groupedHomeworkId)
        }

        rcy_group.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 0) {
                    isFirstInitData = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (isFirstInitData) {
                    recyclerView?.scrollToPosition(0)
                }
            }
        })

    }

    /**
     * 重置章节数据
     * @param position 目录位置
     */
    private fun resetSectionData(position: Int) {
        mSections.clear()
        if (mDirectors[position].subFlag == 1) {
            mSections.addAll(mDirectors[position].subDataList!!)
            val data = mutableListOf<PullBean>()
            for (i in mSections.indices) {
                val pullBean = PullBean()
                pullBean.content = mSections[i].sysDictName!!
                pullBean.searchId = mSections[i].sysDictId!!
                if (i == 0) {
                    pullBean.selected = true
                }
                data.add(pullBean)
            }
            section_group.updateData(data, true)
            section_group.text = mSections[0].sysDictName
            mQueryCondition.chapterId = mSections[0].sysDictId.toString()
        } else {
            section_group.text = ""
            mQueryCondition.chapterId = ""
        }
    }

    /**
     * 重置目录数据
     * @param position 教材位置
     */
    private fun resetDirectorData(position: Int) {
        mDirectors.clear()
        if (mTextBooks[position].subFlag == 1) {
            mDirectors.addAll(mTextBooks[position].subDataList!!)
            val data = mutableListOf<PullBean>()
            for (i in mDirectors.indices) {
                val pullBean = PullBean()
                pullBean.content = mDirectors[i].sysDictName!!
                pullBean.searchId = mDirectors[i].sysDictId!!
                if (i == 0) {
                    pullBean.selected = true
                }
                data.add(pullBean)
            }
            director_group.updateData(data, true)
            director_group.text = mDirectors[0].sysDictName
            //重置查询条件
            mQueryCondition.directoryId = mDirectors[0].sysDictId.toString()
        } else {
            director_group.text = ""
            mQueryCondition.directoryId = ""
        }
    }


    @SuppressLint("CheckResult")
    override fun doResponseData() {
        mViewModel.mGroupData.observe(this, Observer {
            if (it?.flag == RESPONSE_SUCCESS) {
                //重置困难等级
                mDiffLevels.clear()
                mDiffLevels.addAll(it.difficultyList!!)
                mDiffAdapter?.addData(mDiffLevels, true)
                //滚动到第一个item
                isFirstInitData = true //处理由于webview太长，导致scrollToTop不能显示完全的bug
                manager!!.scrollToPositionWithOffset(0, 0)
                //初始化教材数据
                mTextBooks.clear()
                mTextBooks.addAll(it.textBookList!!)
                val data = mutableListOf<PullBean>()
                for (i in mTextBooks.indices) {
                    val pullBean = PullBean()
                    pullBean.content = mTextBooks[i].sysDictName!!
                    pullBean.searchId = mTextBooks[i].sysDictId!!
                    if (i == 0) {
                        pullBean.selected = true
                    }
                    data.add(pullBean)
                }
                textbook_group.updateData(data, true)

                //初始化目录数据
                setDefaultDirecData()
                //初始化章节数据
                setDefaultSectionData()
                //初始化默认查询条件
                if (mTextBooks.size > 0) {
                    textbook_group.text = mTextBooks[0].sysDictName
                    mQueryCondition.textbookId = mTextBooks[0].sysDictId.toString()
                }
                mQueryCondition.groupedHomeworkId = it.groupedHomeworkId!!
            }
        })
        mViewModel.mHomeworkData.observe(this, Observer {
            if (mQueryCondition.page == 0) {
                if (it == null || it.size == 0) {
                    showEmptyView()
                } else {
                    hideEmptyView()
                    mGroupAdapter.addData(it, true)
                    isFirstInitData = true //处理由于webview太长，导致scrollToTop不能显示完全的bug
                    manager!!.scrollToPositionWithOffset(0, 0)
                }
            } else {
                if (it == null || it.size == 0) {
                    toast("没有更多了")
                } else {
                    mGroupAdapter.addData(it)
                }
            }
            mQueryCondition.page++
            rcy_group.stopLoadMore()
            rcy_group.isLoadable = mGroupAdapter.getData().size >= 20
        })

        mViewModel.mSelectedNum.observe(this, Observer {
            select_num_group.text = "已选作业（$it）"
            select_num_group.isEnabled = it!! > 0
        })

        mViewModel.mGroupError.observe(this, Observer {
            toast(it!!)
        })
    }

    /**
     * 设置默认目录，为教材第一个数据
     */
    private fun setDefaultDirecData() {
        mDirectors.clear()
        if (mTextBooks.size > 0 && mTextBooks[0].subFlag == 1) {
            mDirectors.addAll(mTextBooks[0].subDataList!!)
            val diredata = mutableListOf<PullBean>()
            for (i in mDirectors.indices) {
                val pullBean = PullBean()
                pullBean.content = mDirectors[i].sysDictName!!
                pullBean.searchId = mDirectors[i].sysDictId!!
                if (i == 0) {
                    pullBean.selected = true
                }
                diredata.add(pullBean)
            }
            director_group.updateData(diredata, true)
            director_group.text = mDirectors[0].sysDictName
            mQueryCondition.directoryId = mDirectors[0].sysDictId.toString()
        } else {
            director_group.text = ""
            mQueryCondition.directoryId = ""
        }
    }

    /**
     * 设置默认章节，为目录第一个数据
     */

    private fun setDefaultSectionData() {
        mSections.clear()
        if (mDirectors.size > 0 && mDirectors[0].subFlag == 1) {
            mSections.addAll(mDirectors[0].subDataList!!)
            val sectiondata = mutableListOf<PullBean>()
            for (i in mSections.indices) {
                val pullBean = PullBean()
                pullBean.content = mSections[i].sysDictName!!
                pullBean.searchId = mSections[i].sysDictId!!
                if (i == 0) {
                    pullBean.selected = true
                }
                sectiondata.add(pullBean)
            }
            section_group.updateData(sectiondata, true)
            section_group.text = mSections[0].sysDictName
            mQueryCondition.chapterId = mSections[0].sysDictId.toString()
        } else {
            section_group.text = ""
            mQueryCondition.chapterId = ""
        }
    }

    private fun showEmptyView() {
        rcy_group.visibility = View.GONE
        empty_group.visibility = View.VISIBLE
        empty_group.emptyMsgTxt.text = "找不到结果哎，还在努力添加中....."

    }

    private fun hideEmptyView() {
        rcy_group.visibility = View.VISIBLE
        empty_group.visibility = View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun EventToRefresh(event: RefreshEvent) {
        when (event.type) {
            0 -> {
                mQueryCondition.page = 0
                mViewModel.queryHomeworkData(mQueryCondition)
                mViewModel.mSelectedNum.value = event.groupSelectedNum //手动刷新
            }
            1 -> {
                mQueryCondition.page = 0
                mViewModel.loadGroupData(UserInfoConstant.getUserId())
                mQueryCondition.difficultyId = "" //手动重置查询条件
                mQueryCondition.baseFlag = "1"
            }
        }

    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}