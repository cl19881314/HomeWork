package com.xlf.xsrt.work.student.model

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.http.RequestApi
import com.xlf.xsrt.work.student.bean.StuHomwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StudentModel : ViewModel() {
    /**
     * 当月数据
     */
    var mMonthHomeworkData: MutableLiveData<StuHomwork> = MutableLiveData()
    /**
     * 当天数据
     */
    var mDayHomeworkData: MutableLiveData<StuHomwork> = MutableLiveData()
    /**
     * 返回数据错误信息
     */
    var mErrorData: MutableLiveData<String> = MutableLiveData()


    @SuppressLint("CheckResult")
    fun getStuHomeworkByDate(userId: Int, createTime: String) {
        RequestApi.getInstance().getStuHomeworkByDate(userId, createTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.flag == 1)
                        mMonthHomeworkData.value = it
                    else
                        mErrorData.value = it.msg
                }, { e ->
                    mErrorData.value = "网络异常，请检查网络"
                })
    }

    @SuppressLint("CheckResult")
    fun getHomeworkByDay(userId: Int, createTime: String) {
        RequestApi.getInstance().getHomeworkByDay(userId, createTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.flag == 1)
                        mDayHomeworkData.value = it
                    else
                        mErrorData.value = it.msg
                }, { e ->
                    mErrorData.value = "网络异常，请检查网络"
                })
    }

}