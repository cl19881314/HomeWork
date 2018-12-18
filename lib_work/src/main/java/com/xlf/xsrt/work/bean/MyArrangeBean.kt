package com.xlf.xsrt.work.bean

/**
 * @author Chenhong
 * @date 2018/12/18.
 * @des
 */
class MyArrangeBean {
    var flag = 0
    var msg = ""
    //是否为预约作业 0否  1是
    var appointmentFlag = 0
    var homeworkList: ArrayList<SysDictVo>? = null
    var homeworkBaseList: ArrayList<HomeworkBaseVo>? = null
}