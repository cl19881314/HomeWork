package com.xlf.xsrt.work.teacher.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.detail.SubjectDetailActivity
import com.xlf.xsrt.work.eventbus.NeedRefreshSuccessBean
import com.xlf.xsrt.work.teacher.group.adapter.SelectedHomeworkAdapter
import com.xlf.xsrt.work.teacher.group.viewmodel.SelectedHomeWorkModel
import com.xlf.xsrt.work.utils.DateUtil
import com.xlf.xsrt.work.utils.MaxTextLengthFileter
import com.xlf.xsrt.work.widget.CustomDatePicker
import com.xlf.xsrt.work.widget.TitleBar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.xsrt_activity_group_teacher.*
import kotlinx.android.synthetic.main.xsrt_activity_selected_homework.*
import kotlinx.android.synthetic.main.xsrt_layout_arrage_dailog.view.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit

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
    private var mCustomDatePicker: CustomDatePicker? = null

    private var flag = 1 //0为预约 1为布置
    private var mDefaultHomeworkName = ""

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
        val calendar = Calendar.getInstance()
        val start = DateUtil.dateToString(calendar.time, "yyyy-MM-dd HH:mm")
        val nowYear = calendar.get(Calendar.YEAR)
        calendar.set(Calendar.YEAR, nowYear + 20)
        val end = DateUtil.dateToString(calendar.time, "yyyy-MM-dd HH:mm")
//        grouphomework_name_select.text = DateUtil.chainToString2(start)//不用给默认当前时间
        mCustomDatePicker = CustomDatePicker(this, { time ->
            //预约作业
            val timeOfLong = DateUtil.string2date(time, "yyyy-MM-dd HH:mm")?.time
            doSubscribeHomeWork(timeOfLong!!)
        }, start, end)
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

        btn_subscribe_selected_homework.setOnClickListener {
            val now = DateUtil.dateToString(Calendar.getInstance().time, "yyyy-MM-dd HH:mm")
            mCustomDatePicker?.show(now)
        }
        btn_sure_selected_homework.setOnClickListener {
            AlertDialog.Builder(this@SelectedHomeWorkActivity)
                    .setTitle("是否确认布置该作业")
                    .setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("确定") { dialog, which ->
                        doCommitHomeWork()
                        dialog.dismiss()
                    }
                    .show()
        }
        grouphomework_name_select.setOnClickListener {
            val editText = EditText(this@SelectedHomeWorkActivity)
            editText.filters = Array<InputFilter>(1) { MaxTextLengthFileter(this, 20) }
            AlertDialog.Builder(this@SelectedHomeWorkActivity)
                    .setTitle("修改名称")
                    .setView(editText)
                    .setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("确定") { dialog, which ->
                        if (!TextUtils.isEmpty(editText.text.trim())) {
                            grouphomework_name_select.text = editText.text
                        } else {
                            grouphomework_name_select.text = mDefaultHomeworkName
                        }
                        dialog.dismiss()
                    }
                    .show()


        }
        mAdapter.setOnItemChildViewClickListener(object : BaseRcyAdapter.ItemChildViewClickListener {
            override fun onItemChildClick(childView: View, position: Int) {
                when (childView.id) {
                    R.id.dellImg -> {
                        AlertDialog.Builder(this@SelectedHomeWorkActivity)
                                .setTitle("是否确定将该题目移除作业？")
                                .setPositiveButton("确定") { dialog, which ->
                                    dialog.dismiss()
                                    val bean = mAdapter.getItemContent(position)
                                    RequestApi.getInstance().addOrCancleHomework(UserInfoConstant.getUserId(), 0, mGroupHomeworkId, bean!!.homeworkId.toString())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe {
                                                when (it.flag) {
                                                    1 -> mAdapter.removeData(position)
                                                    0 -> toast(it.msg!!)
                                                }
                                            }
                                }
                                .setNegativeButton("取消") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .show()
                    }
                    R.id.showDetailTxt -> {
                        val bean = mAdapter.getItemContent(position)
                        var intent = Intent(this@SelectedHomeWorkActivity, SubjectDetailActivity::class.java)
                        intent.putExtra("url", bean?.homeworkDetailUrl)
                        intent.putExtra("num", bean?.homeworkId.toString())
                        startActivity(intent)
                    }
                }
            }

        })
    }

    override fun doResponseData() {
        mViewModel.mGroupHomeworkData.observe(this, Observer {
            grouphomework_name_select.text = it?.homeworkName
            mDefaultHomeworkName = it?.homeworkName!!
            mAdapter.addData(it?.groupedhomeworkList!!, true)
            if (mAdapter.getData().size > 0) {
                btn_sure_selected_homework.isEnabled = true
                btn_subscribe_selected_homework.isEnabled = true
            } else {
                btn_sure_selected_homework.isEnabled = false
                btn_subscribe_selected_homework.isEnabled = false
            }
        })

        mViewModel.mPushData.observe(this, Observer {
            if (it?.flag == 1) {
                EventBus.getDefault().post(NeedRefreshSuccessBean())
                if (flag == 0) {
                    //预约成功
                    showArrangeDailog("预约布置成功", R.drawable.xsrt_tc_success_icon, true)
                } else if (flag == 1) {
                    //发布成功
                    showArrangeDailog("布置成功", R.drawable.xsrt_tc_success_icon, true)
                }
            } else {
                if (flag == 0) {
                    //预约失败
                    showArrangeDailog("预约布置失败", R.drawable.xsrt_tc_fail_icon, false)
                } else if (flag == 1) {
                    //发布失败
                    showArrangeDailog("布置失败", R.drawable.xsrt_tc_fail_icon, false)
                }
            }
        })

        mViewModel.mError.observe(this, Observer {
            toast(it!!)
        })

    }

    private fun showArrangeDailog(text: String, resId: Int, success: Boolean) {
        val drawable = ResourcesCompat.getDrawable(resources, resId, null)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)//必须设置图片大小，否则不显示
        val rootview = LayoutInflater.from(this).inflate(R.layout.xsrt_layout_arrage_dailog, null)
        rootview.tv_arrage_dailog.text = text
        rootview.tv_arrage_dailog.setCompoundDrawables(null, drawable, null, null)
        val dialog = AlertDialog.Builder(this@SelectedHomeWorkActivity)
                .setView(rootview)
                .create()
        dialog.setOnShowListener {
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { v ->
                        it.dismiss()
                        if (success) {
                            finish()
                        }
                    }
        }
        dialog.show()

    }

    /**
     * 确认布置
     */
    private fun doCommitHomeWork() {
        flag = 1
        mViewModel.pushHomeWork(UserInfoConstant.getUserId(), "", mGroupHomeworkId, grouphomework_name_select.text.toString())
    }

    /**
     * 预约布置
     */
    private fun doSubscribeHomeWork(time: Long) {
        flag = 0
        mViewModel.pushHomeWork(UserInfoConstant.getUserId(), time.toString(), mGroupHomeworkId, grouphomework_name_select.text.toString())
    }
}