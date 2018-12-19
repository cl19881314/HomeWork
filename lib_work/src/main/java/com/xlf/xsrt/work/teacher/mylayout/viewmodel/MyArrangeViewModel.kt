package com.xlf.xsrt.work.teacher.mylayout.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.MyArrangeBean
import com.xlf.xsrt.work.bean.BaseEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Chenhong
 * @date 2018/12/18.
 * @des
 */
class MyArrangeViewModel : ViewModel() {
    var mGroupData: MutableLiveData<MyArrangeBean> = MutableLiveData()
    var mDeleteData: MutableLiveData<BaseEntry> = MutableLiveData()

    fun getArrangeData(userId: Int, groupedHomeworkId: Int) {
        RequestApi.getInstance().getMyArrangeData(userId, groupedHomeworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mGroupData.value = it
                }
    }

    fun deleteAppointmentWork(userId: Int, groupedHomeworkId: Int) {
        RequestApi.getInstance().deleteAppointmentWork(userId, groupedHomeworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mDeleteData.value = it
                }
    }
}