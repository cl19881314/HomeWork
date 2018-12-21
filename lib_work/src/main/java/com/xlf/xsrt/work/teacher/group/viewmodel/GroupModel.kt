package com.xlf.xsrt.work.teacher.group.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.GroupeEntry
import com.xlf.xsrt.work.bean.HomeworkBaseVo
import com.xlf.xsrt.work.bean.QueryCondition
import com.xlf.xsrt.work.teacher.group.bean.AddRespondeBean
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
     * 返回数据错误信息
     */
    var mSelectedNum: MutableLiveData<Int> = MutableLiveData()

    /**
     * user数据
     *
     */
//    var mUserData: MutableLiveData<UserInfoConstant> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadGroupData(userId: Int) {
        RequestApi.getInstance().queryGroupData(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    mSelectedNum.value = it.groupedCount
                    mGroupData.value = it
                    mHomeworkData.value = it.homeworkBaseList
                }, { e ->
                    mGroupError.value = "网络异常，请检查网络"
                })
    }

    @SuppressLint("CheckResult")
    fun queryHomeworkData(condition: QueryCondition) {
        RequestApi.getInstance().queryHomeworkData(condition.userId, condition.textbookId, condition.directoryId, condition.chapterId, condition.baseFlag, condition.difficultyId, condition.page)
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

    @SuppressLint("CheckResult")
    fun addOrCancleHomework(teacherId: Int, addFlag: Int, groupedHomework: Int, homeworkIds: String) {
        RequestApi.getInstance().addOrCancleHomework(teacherId, addFlag, groupedHomework, homeworkIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.flag == 1) {
                        mSelectedNum.value = it.groupedCount
                    } else {
                        mGroupError.value = it.msg
                    }
                }, { e ->
                    mGroupError.value = "网络异常，请检查网络"
                })

    }
}