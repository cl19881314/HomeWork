package com.xlf.xsrt.work.teacher.group.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.entry.GroupeEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GroupModel : ViewModel() {
    var mGroupData: MutableLiveData<GroupeEntry> = MutableLiveData()
    var mGroupError: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadGroupData(userId: Int) {
        RequestApi.getInstance().queryGroupData(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    mGroupData.value = it
                }, { e ->
                    mGroupError.value = "网络异常，请检查网络"
                })
    }
}