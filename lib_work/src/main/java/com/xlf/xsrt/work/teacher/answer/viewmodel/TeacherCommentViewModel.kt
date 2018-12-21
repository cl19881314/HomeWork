package com.xlf.xsrt.work.teacher.answer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.teacher.answer.bean.TeacherCommentBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Chenhong
 * @date 2018/12/19.
 * @des
 */
class TeacherCommentViewModel : ViewModel(){
    var mCommentViewModel = MutableLiveData<TeacherCommentBean>()
    var mErrorData: MutableLiveData<String> = MutableLiveData()
    fun getTeacherCommentData(stuAnswerId : Int){
        RequestApi.getInstance().getTeacherCommentData(stuAnswerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mCommentViewModel.value = it
                },{
                    mErrorData.value = "网络异常，请检查网络"
                })
    }
}