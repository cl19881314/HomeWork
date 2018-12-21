package com.xlf.xsrt.work.teacher.group.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.BaseEntry
import com.xlf.xsrt.work.teacher.group.bean.GroupHomeWorkBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectedHomeWorkModel : ViewModel() {
    /**
     * 已选作业数据
     */
    var mGroupHomeworkData: MutableLiveData<GroupHomeWorkBean> = MutableLiveData()
    /**
     * 返回数据错误信息
     */
    var mError: MutableLiveData<String> = MutableLiveData()

    /**
     * 提交作业
     */
    var mPushData: MutableLiveData<BaseEntry> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun pushHomeWork(teacherId: Int, appointmentTime: String, groupedHomeworkId: Int, homeworkName: String) {
        RequestApi.getInstance().pushHomeWork(teacherId, appointmentTime, groupedHomeworkId, groupedHomeworkId, homeworkName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.flag == 1) {
                        mPushData.value = it
                    } else {
                        mError.value = it.msg
                    }
                }, { e ->
                    mError.value = "网络异常，请检查网络"
                })
    }

    @SuppressLint("CheckResult")
    fun loadSelectHomework(userId: Int, mGroupHomeworkId: Int) {
        RequestApi.getInstance().querySelectHomework(userId, mGroupHomeworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    mGroupHomeworkData.value = it
                }, { e ->
                    mError.value = "网络异常，请检查网络"
                })
    }
}