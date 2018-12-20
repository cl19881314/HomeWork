package com.xlf.xsrt.work.teacher.answer.bean

import com.xlf.xsrt.work.bean.BaseEntry

/**
 * @author Chenhong
 * @date 2018/12/18.
 * @des 教师端 学生作业
 */
class StudentAnswerBean : BaseEntry() {
    var classList: ArrayList<ClassVo>? = null
    var createTimeList: ArrayList<ClassVo>? = null
    var homeworkList: ArrayList<HomeworkVo>? = null
    var stuAnswerList: ArrayList<StuAnswerVo>? = null

    class ClassVo {
        var createTime = ""
        var classId = -1
        var className = ""
    }

    class HomeworkVo {
        var homeworkName = ""
        var homeworkId = -1
    }

    class StuAnswerVo {
        var stuName = ""
        var homeworkName = ""
        var wrongCount = 0
        var totalCount = 0
        //是否批阅，0表示待批阅 1 已批阅
        var state = -1
        var stuAnswerId = -1
        var homeworkId = -1
        var answerTime = ""
        var homeworkDetailUrlList: ArrayList<String>? = null
    }
}