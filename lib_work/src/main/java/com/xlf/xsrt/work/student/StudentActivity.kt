package com.xlf.xsrt.work.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.eventbus.RefreshEvent
import com.xlf.xsrt.work.student.adapter.StudentAdapter
import com.xlf.xsrt.work.student.model.StudentModel
import com.xlf.xsrt.work.utils.DateUtil
import com.xlf.xsrt.work.widget.TitleBar
import com.xlf.xsrt.work.widget.calendar.CalendarBean
import kotlinx.android.synthetic.main.xsrt_activity_student.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * 学生主界面
 */
class StudentActivity : BaseActivity() {
    //查询的时间
    private var queryMonthTime = ""
    private var queryDayTime = ""
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(StudentModel::class.java)
    }
    private val mAdapter by lazy {
        StudentAdapter()
    }

    companion object {
        fun start(ctx: Context) {
            val intent = Intent(ctx, StudentActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student
    }

    override fun init() {
        rcy_student.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcy_student.adapter = mAdapter
        EventBus.getDefault().register(this)
        mViewModel.getStuHomeworkByDate(UserInfoConstant.getUserId(), queryMonthTime)
        mViewModel.getHomeworkByDay(UserInfoConstant.getUserId(), queryDayTime)
    }

    override fun initListener() {
        titlebar_student.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
            }
        })

        ibtn_premonth_stu.setOnClickListener { calendar_stu.moveToPreMonth() }
        ibtn_nextmonth_stu.setOnClickListener { calendar_stu.moveToNextMonth() }
        calendar_stu.setOnCanlendarPageSelectListener { year, month ->
            tv_time_stu.text = "${year}年${month}月"
            val instance = Calendar.getInstance()
            instance.set(year, month - 1, 1)
            queryMonthTime = DateUtil.dateToString(instance.time, "yyyy-MM-dd")
            mViewModel.getStuHomeworkByDate(UserInfoConstant.getUserId(), queryMonthTime)
        }
        calendar_stu.setOnCanlendarItemListener { calendar, position ->
            //获取数据 刷新recyvleview
            val instance = Calendar.getInstance()
            instance.set(calendar.year, calendar.month - 1, calendar.day)
            queryDayTime = DateUtil.dateToString(instance.time, "yyyy-MM-dd")
            mViewModel.getHomeworkByDay(UserInfoConstant.getUserId(), queryDayTime)
        }

    }

    override fun doResponseData() {
        /**
         * 按月查询当月数据
         */
        mViewModel.mMonthHomeworkData.observe(this, Observer {
            //更新日历当日时间
            val today = CalendarBean()
            val current = DateUtil.string2date(it!!.currentTime, "yyyy-MM-dd")
            val instance = Calendar.getInstance()
            instance.time = current
            today.year = instance.get(Calendar.YEAR)
            today.month = instance.get(Calendar.MONTH) + 1
            today.day = instance.get(Calendar.DAY_OF_MONTH)
            today.isCurrentDay = true
            calendar_stu.updateCurrentDay(today, false)//只设置数据不绘制
            //填充数据

            val courseInfos = mutableListOf<CalendarBean>()
            for (i in 0 until it.homeworkTimeList!!.size) {
                val courseInfosVo = it.homeworkTimeList!![i]
                val date = DateUtil.string2date(courseInfosVo.createTime, "yyyy-MM-dd")
                val instance = Calendar.getInstance()
                instance.time = date
                val calendar = CalendarBean()
                calendar.year = instance.get(Calendar.YEAR)
                calendar.month = instance.get(Calendar.MONTH) + 1
                calendar.day = instance.get(Calendar.DAY_OF_MONTH)
                calendar.timeState = courseInfosVo.timeState
                courseInfos.add(calendar)
            }
            calendar_stu.setSchemeDate(courseInfos)//绘制刷新
            //跳转到当月（初始化时才跳转）
            if (TextUtils.isEmpty(queryMonthTime.trim())) {
                tv_time_stu.text = "${today.year}年${today.month}月"//初始化title
                calendar_stu.moveToDate(today.year, today.month)
            }
        })


        mViewModel.mDayHomeworkData.observe(this, Observer {
            if (it!!.homeworkList != null && it.homeworkList!!.size > 0) {
                hideNoDataView()
                mAdapter.addData(it.homeworkList!!, true)
            } else {
                showNoDataView()
            }
        })
    }

    private fun showNoDataView() {
        rl_student.visibility = View.GONE
        empty_student.visibility = View.VISIBLE
    }

    private fun hideNoDataView() {
        rl_student.visibility = View.VISIBLE
        empty_student.visibility = View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventToRefresh(event: RefreshEvent) {
        mViewModel.getStuHomeworkByDate(UserInfoConstant.getUserId(), queryMonthTime)
        mViewModel.getHomeworkByDay(UserInfoConstant.getUserId(), queryDayTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}