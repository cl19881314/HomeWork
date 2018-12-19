package com.xlf.xsrt.work.bean

class UserInfoVo : BaseEntry() {
    var userInfoVo: UserInfoVo? = null

    class UserInfoVo {
        var userId = -1
        var userType = 1 //0为未选择 1为学生 2为老师 3为家长
    }
}