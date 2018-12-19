package com.xlf.xsrt.work.teacher.answer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.bean.BaseEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Chenhong
 * @date 2018/12/19.
 * @des
 */
class StuAnswerDetailViewModel : ViewModel() {
    var mAddCommentViewModel = MutableLiveData<BaseEntry>()
    fun addComment(stuAnswerId: Int, comment: String) {
        RequestApi.getInstance().setTeacherCommentData(stuAnswerId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mAddCommentViewModel.value = it
                }, {

                })
    }

    fun getStuAnswerDetail(stuAnswerId: Int, comment: String) {
        RequestApi.getInstance().getStuAnswerDetailData(stuAnswerId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mAddCommentViewModel.value = it
                }, {

                })
    }


}