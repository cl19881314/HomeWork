package com.xlf.xsrt.work.teacher.group.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SelectedHomeWorkModel : ViewModel() {
    /**
     * 返回数据错误信息
     */
    var mGroupError: MutableLiveData<String> = MutableLiveData()

    fun PushHomeWork(teacherId: Int, appointmentTime: Long) {

    }
}