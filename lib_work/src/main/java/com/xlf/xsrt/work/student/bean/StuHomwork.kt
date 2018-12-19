package com.xlf.xsrt.work.student.bean

import com.xlf.xsrt.work.bean.BaseEntry

/**
 * 日历界面按月查询返回数据
 */
class StuHomwork : BaseEntry() {
    var currentTime = ""
    /**
     * 某月整月数据
     */
    var homeworkTimeList: MutableList<HomeworkStuVo>? = null
    /**
     * 当天数据集合
     */
    var homeworkList: MutableList<HomeworkStuVo>? = null

}