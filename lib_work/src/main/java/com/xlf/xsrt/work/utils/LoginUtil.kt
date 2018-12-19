package com.xlf.xsrt.work.utils

import android.annotation.SuppressLint
import android.content.Context
import com.xlf.xsrt.work.base.RequestApi
import com.xlf.xsrt.work.student.StudentActivity
import com.xlf.xsrt.work.teacher.TeacherActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginUtil {
    companion object {
        @SuppressLint("CheckResult")
        fun enterHomeWork(ctx: Context, token: String) {
            if (token == "token1") {
                StudentActivity.start(ctx, 111111)
            } else if ("token2" == token) {
                TeacherActivity.start(ctx, 222222)
            }

//            RequestApi.getInstance().queryUserInfo(token)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        if (it.userInfoVo!!.userType == 1) {//学生
//                            StudentActivity.start(ctx, it.userInfoVo!!.userId)
//                        } else if (it.userInfoVo!!.userType == 2) {
//                            TeacherActivity.start(ctx, it.userInfoVo!!.userId)
//                        }
//                    }, {
//
//                    })
        }
    }
}