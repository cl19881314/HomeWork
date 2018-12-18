package com.xlf.xsrt.work.teacher.mylayout.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xlf.xsrt.work.bean.GroupeEntry

/**
 * @author Chenhong
 * @date 2018/12/18.
 * @des
 */
class MyArrangeViewModel : ViewModel(){
    var mGroupData: MutableLiveData<GroupeEntry> = MutableLiveData()
}