package com.xlf.xsrt.work.teacher.group.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.teacher.group.bean.GroupeEntry
import com.xlf.xsrt.work.teacher.group.bean.HomeworkBaseVo
import com.xlf.xsrt.work.teacher.group.bean.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GroupModel : ViewModel() {
    /**
     * 首次加载组作业数据
     */
    var mGroupData: MutableLiveData<GroupeEntry> = MutableLiveData()
    /**
     * 分页查询组作业
     */
    var mHomeworkData: MutableLiveData<MutableList<HomeworkBaseVo>> = MutableLiveData()
    /**
     * 返回数据错误信息
     */
    var mGroupError: MutableLiveData<String> = MutableLiveData()

    /**
     * user数据
     *
     */
//    var mUserData: MutableLiveData<UserInfo> = MutableLiveData()

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

    @SuppressLint("CheckResult")
    fun queryHomeworkData(userId: Int, textbookId: Int, directoryId: Int, chapterId: Int, baseFlag: Int, difficultyId: Int, page: Int) {
        RequestApi.getInstance().queryHomeworkData(userId, textbookId, directoryId, chapterId, baseFlag, difficultyId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.flag == 1) {
                        mHomeworkData.value = it.homeworkBaseList
                    } else {
                        mGroupError.value = it.msg
                    }

                }, { e ->
                    mGroupError.value = "网络异常，请检查网络"
                })
    }
//
//    @SuppressLint("CheckResult")
//    fun queryUserInfo(token: String) {
//        RequestApi.getInstance().queryUserInfo(token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ it ->
//                    if (it.flag == 1) {
//                        mUserData.value = it
//                    } else {
//                        mGroupError.value = it.msg
//                    }
//
//                }, { e ->
//                    mGroupError.value = "网络异常，请检查网络"
//                })
//    }
}