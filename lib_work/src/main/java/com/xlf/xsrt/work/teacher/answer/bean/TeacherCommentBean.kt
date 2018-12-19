package com.xlf.xsrt.work.teacher.answer.bean

import com.xlf.xsrt.work.bean.BaseEntry

/**
 * @author Chenhong
 * @date 2018/12/19.
 * @des
 */
class TeacherCommentBean : BaseEntry() {
    var teacherComment: TeacherCommentVo? = null

    class TeacherCommentVo {
        var stuName = ""
        var createTime = ""
        var comment = ""
    }
}