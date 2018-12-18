package com.xlf.xsrt.work.bean

import com.xlf.xsrt.work.entry.BaseEntry

class UserInfo : BaseEntry() {
    var userInfoVo: UserInfoVo? = null

    class UserInfoVo {
        var userId: Int? = null
        var userType: Int? = null //0为未选择 1为学生 2为老师 3为家长
    }
}