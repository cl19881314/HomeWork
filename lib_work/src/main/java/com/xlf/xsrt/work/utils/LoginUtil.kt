package com.xlf.xsrt.work.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import com.xlf.xsrt.work.http.RequestApi
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.student.StudentActivity
import com.xlf.xsrt.work.teacher.TeacherActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginUtil {
    companion object {
        @SuppressLint("CheckResult")
        fun login(act: Activity, user: String, pwd: String) {
            RequestApi.getInstance().login(user, pwd).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.flag == 1) {
                            getUserMsg(act, it.token)
                        }
                    }, {

                    })
        }


        @SuppressLint("CheckResult")
        fun getUserMsg(act: Activity, token: String) {
            RequestApi.getInstance().queryUserInfo(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (act.isFinishing) {
                            return@subscribe
                        }
                        if (it.flag == 1) {
                            if (it.userInfoVo!!.userType == 1) {//学生
                                StudentActivity.start(act)
                            } else if (it.userInfoVo!!.userType == 2) {//老师
                                TeacherActivity.start(act)
                            }
                            UserInfoConstant.setUserId(it.userInfoVo!!.userId)
                        } else {
                            Toast.makeText(act, "用户token失效，请重新登录", Toast.LENGTH_SHORT).show()
                        }
                    }, {

                    })
        }
    }
}