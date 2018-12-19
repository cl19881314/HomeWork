package com.xlf.xsrt.work.bean

class UserInfo : BaseEntry() {
    var userInfoVo: UserInfoVo? = null

    class UserInfoVo {
        var userId: Int? = null
        var userType: Int? = null //0为未选择 1为学生 2为老师 3为家长
    }
}