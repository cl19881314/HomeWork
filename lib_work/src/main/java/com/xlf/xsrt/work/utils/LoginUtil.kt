package com.xlf.xsrt.work.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
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
            getUserMsg(ctx, token)
        }

        fun login(ctx: Context, user: String, pwd: String) {
            RequestApi.getInstance().login(user, pwd).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.flag == 1) {
                            getUserMsg(ctx, it.token)
                        }
                    }, {

                    })
        }


        private fun getUserMsg(ctx: Context, token: String) {
            RequestApi.getInstance().queryUserInfo(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if ((ctx as Activity).isFinishing) {
                            return@subscribe
                        }
                        if (it.flag == 1) {
                            if (it.userInfoVo!!.userType == 1) {//学生
                                StudentActivity.start(ctx, it.userInfoVo!!.userId)
                            } else if (it.userInfoVo!!.userType == 2) {
                                TeacherActivity.start(ctx, it.userInfoVo!!.userId)
                            }
                        } else {
                            Toast.makeText(ctx, "用户token失效，请重新登录", Toast.LENGTH_SHORT).show()
                        }
                    }, {

                    })
        }
    }
}