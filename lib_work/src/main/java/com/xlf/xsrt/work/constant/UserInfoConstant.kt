package com.xlf.xsrt.work.constant

/**
 * @author Chenhong
 * @date 2018/12/20.
 * @des
 */
object UserInfoConstant{
    private var mUserId = -1
    fun getUserId() : Int{
        if (mUserId != -1 || mUserId != 0){
            return mUserId
        }
            //TODO 读取sp
            return  1
    }

    fun setUserId(userId : Int){
        mUserId = userId
    }
}