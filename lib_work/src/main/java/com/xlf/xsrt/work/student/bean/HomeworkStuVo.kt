package com.xlf.xsrt.work.student.bean

/**
 * 日历界面作业数据
 */
class HomeworkStuVo {
    var homeworkId = -1
    var createTile = ""
    var timeState = 0 //某天作业状态0，有未提交，1全部提交
    var homeworkName = ""
    var subjectName = ""
    var stuAnswerId = -1
    var createTime = ""
    var state = 0 //状态 0待提交 1待批阅 2已批阅
    var homeworkAnalysisUrlList: MutableList<String>? = null
}