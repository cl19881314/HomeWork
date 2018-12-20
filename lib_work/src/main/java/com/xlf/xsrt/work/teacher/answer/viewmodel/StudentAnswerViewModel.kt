package com.xlf.xsrt.work.teacher.answer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Chenhong
 * @date 2018/12/18.
 * @des 学生作业
 */
class StudentAnswerViewModel : ViewModel() {
    var mAnswerViewModel = MutableLiveData<StudentAnswerBean>()

    fun getStudentAnswerData(userId: Int, classId: Int, createTime: String, homeworkId: Int) {
        RequestApi.getInstance().getStudentAnswerData(userId,classId, createTime, homeworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mAnswerViewModel.value = it
                }, {

                })
    }
}