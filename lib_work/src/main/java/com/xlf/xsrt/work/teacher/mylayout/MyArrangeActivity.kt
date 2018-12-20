package com.xlf.xsrt.work.teacher.mylayout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.bean.SysDictVo
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.teacher.mylayout.adapter.MyArrangeAdapter
import com.xlf.xsrt.work.teacher.mylayout.viewmodel.MyArrangeViewModel
import com.xlf.xsrt.work.widget.pulltextview.PullBean
import com.xlf.xsrt.work.widget.pulltextview.PullTextView
import kotlinx.android.synthetic.main.xsrt_activity_my_arrange_layout.*
import kotlinx.android.synthetic.main.xsrt_item_empty_layout.*

/**
 * 我的布置
 */
class MyArrangeActivity : BaseActivity() {
    private var mAdapter: MyArrangeAdapter? = null
    private var mGroupWorkId = -1
    private var mHomeworkList: ArrayList<SysDictVo>? = null
    private val mViewModer by lazy {
        ViewModelProviders.of(this@MyArrangeActivity).get(MyArrangeViewModel::class.java)
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_my_arrange_layout
    }

    override fun init() {
        titleBar.getBackImageView()?.setOnClickListener {
            finish()
        }
        mAdapter = MyArrangeAdapter()
        showDataRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        showDataRv.adapter = mAdapter
        showDataRv.isLoadable = false
        mViewModer.getArrangeData(UserInfoConstant.getUserId(), -1)
    }

    override fun initListener() {
        timePullTxt.setItemClickListener(object :PullTextView.PullListItemListener{
            override fun onItemClick(bean: PullBean, position: Int) {
                var timeBean = mHomeworkList!![position]
                addWorkPullData(timeBean)
                if (timeBean.subFlag == 1) {
                    mGroupWorkId = timeBean.subDataList!![0].sysDictId!!
                    mViewModer.getArrangeData(UserInfoConstant.getUserId(), mGroupWorkId)
                }
            }
        })
        homeWorkPullTxt.setItemClickListener(object :PullTextView.PullListItemListener{
            override fun onItemClick(bean: PullBean, position: Int) {
                mGroupWorkId = bean.searchId
                mViewModer.getArrangeData(UserInfoConstant.getUserId(), mGroupWorkId)
            }
        })
        timePullTxt.setOnClickListener {
            homeWorkPullTxt.hidePop()
            if (timePullTxt.isChecked){
                timePullTxt.showPop()
            } else {
                timePullTxt.hidePop()
            }
        }
        homeWorkPullTxt.setOnClickListener {
            timePullTxt.hidePop()
            if (homeWorkPullTxt.isChecked){
                homeWorkPullTxt.showPop()
            } else {
                homeWorkPullTxt.hidePop()
            }
        }

        delTxt.setOnClickListener {
            AlertDialog.Builder(this@MyArrangeActivity).setTitle("提示")
                    .setMessage("是否确认删除该预约布置")
                    .setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("确认删除"){ dialog, which ->
                        mViewModer.deleteAppointmentWork(UserInfoConstant.getUserId(), mGroupWorkId)
                        dialog.dismiss()
                    }
                    .show()
        }
    }

    override fun doResponseData() {
        mViewModer.mGroupData.observe(this, Observer{
            if (it?.flag == 1){
                arrangedStatusTxt.visibility = if (it?.appointmentFlag == 0) View.VISIBLE else View.GONE
                arrangingStatusTxt.visibility = if (it?.appointmentFlag == 1) View.VISIBLE else View.GONE
                delTxt.visibility = if (it?.appointmentFlag == 1) View.VISIBLE else View.GONE
                if (it?.homeworkBaseList?.size ?: -1 > 0) {
//                    showDataRv.visibility = View.VISIBLE
//                    emptyLayout.visibility = View.GONE
//                    noDataTxt.visibility = View.GONE
                    mAdapter!!.setArrangeData(it?.homeworkBaseList)
                } else {
//                    showDataRv.visibility = View.GONE
//                    arrangedStatusTxt.visibility = View.GONE
//                    arrangingStatusTxt.visibility = View.GONE
//                    noDataTxt.visibility = View.VISIBLE
//                    emptyLayout.visibility = View.VISIBLE
                }
                if (it?.homeworkList?.size ?: -1 > 0) {
                    mHomeworkList = it?.homeworkList
                    var bean = mHomeworkList!![0]
                    addTimePullData(bean)
                    addWorkPullData(bean)
                }
            }
        })

        mViewModer.mDeleteData.observe(this, Observer {
            if (it?.flag == 1) {
                toast("删除成功")
                mViewModer.getArrangeData(UserInfoConstant.getUserId(), -1)
            } else {
                toast("删除失败")
            }
        })
    }

    private fun addTimePullData(bean: SysDictVo) {
        timePullTxt.text = bean.sysDictName
        var list =  mutableListOf<PullBean>()
        for (time in mHomeworkList!!){
            var data = PullBean()
            data.searchId = time.sysDictId!!
            data.content = time.sysDictName!!
            list.add(data)
        }
        timePullTxt.updateData(list,true)
    }

    private fun addWorkPullData(bean: SysDictVo?) {
        if (bean?.subFlag == 1){
            var workList = bean.subDataList!!
            homeWorkPullTxt.text = workList[0].sysDictName
            var list =  mutableListOf<PullBean>()
            for (time in workList){
                var data = PullBean()
                data.searchId = time.sysDictId!!
                data.content = time.sysDictName!!
                list.add(data)
            }
            homeWorkPullTxt.updateData(list, true)
        }
    }

}