package com.xlf.xsrt.work.student.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.BaseEntry
import com.xlf.xsrt.work.student.bean.AnalysisDataBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Chenhong
 * @date 2018/12/21.
 * @des
 */
class StudentAnswerModel : ViewModel(){
    var mStudentModel = MutableLiveData<BaseEntry>()
    var mAnalysisModel = MutableLiveData<AnalysisDataBean>()
    var mErrorData: MutableLiveData<String> = MutableLiveData()
    fun submitAnswer(userId : Int, groupId : Int, answer : String){
        RequestApi.getInstance().submitStudentAnswer(userId, groupId, answer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mStudentModel.value = it
                },{
                    mErrorData.value = "网络异常，请检查网络"
                })
    }

    fun getAnalysis(userId : Int, homeworkId : Int){
        RequestApi.getInstance().getAnaysyPage(userId, homeworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mAnalysisModel.value = it
                },{
                    mErrorData.value = "网络异常，请检查网络"
                })
    }
}