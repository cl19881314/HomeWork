package com.xlf.xsrt.work.teacher.mylayout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.teacher.mylayout.adapter.MyArrangeAdapter
import com.xlf.xsrt.work.teacher.mylayout.viewmodel.MyArrangeViewModel
import kotlinx.android.synthetic.main.xsrt_activity_my_arrange_layout.*

/**
 * 我的布置
 */
class MyArrangeActivity : BaseActivity() {
    private var mAdapter: MyArrangeAdapter? = null
    private var mGroupWorkId = -1
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
        mViewModer.getArrangeData(mUserId, -1)
        mViewModer.mGroupData.observe(this, Observer{
            if (it?.flag == 1){
                arrangedStatusTxt.visibility = if (it?.appointmentFlag == 0) View.VISIBLE else View.GONE
                arrangingStatusTxt.visibility = if (it?.appointmentFlag == 1) View.VISIBLE else View.GONE
                delTxt.visibility = if (it?.appointmentFlag == 1) View.VISIBLE else View.GONE
                mAdapter!!.setArrangeData(it?.homeworkBaseList)
                if (it?.homeworkList?.size ?: -1 > 0) {
                    mGroupWorkId = it?.homeworkList!![0].sysDictId!!
                }
            } else if (it?.flag == 0){
//                toast("")
            }
        })

        mViewModer.mDeleteData.observe(this, Observer {
            if (it?.flag == 1) {
                mViewModer.getArrangeData(mUserId, -1)
            }
        })

        delTxt.setOnClickListener {
            mViewModer.deleteAppointmentWork(mUserId, mGroupWorkId)
        }
    }

}