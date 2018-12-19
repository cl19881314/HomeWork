package com.xlf.xsrt.work.student.model

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.BaseStudentEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReadOvreModel : ViewModel() {
    var mReadOverData: MutableLiveData<BaseStudentEntry> = MutableLiveData()
    /**
     * 返回数据错误信息
     */
    var mReadOverError: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getTeacherComment(stuAnswerId: Int) {
        RequestApi.getInstance().getTeacherComment(stuAnswerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    mReadOverData.value = it
                }, { e ->
                    mReadOverError.value = "网络异常，请检查网络"
                })
    }
}