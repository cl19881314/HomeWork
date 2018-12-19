package com.xlf.xsrt.work.bean


/**
 * 组作业界面实体类
 */
class GroupeEntry : BaseEntry() {
    var subjectId: Int? = null
    var groupedHomeworkId: Int? = null
    var groupedCount: Int? = null
    var homeworkBaseList: MutableList<HomeworkBaseVo>? = null//题集合
    var difficultyList: MutableList<SysDictVo>? = null//难度选项
    var textBookList: MutableList<SysDictVo>? = null //教材下来数据
}