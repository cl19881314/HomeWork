package com.xlf.xsrt.work.entry


/**
 * 组作业界面实体类
 */
class GroupeEntry : BaseEntry() {
    var subjectId: Int? = null
    var groupedHomeworkId: Int? = null
    var groupedCount: Int? = null
    var homeworkBaseList: List<HomeworkBaseVo>? = null//题集合
    var difficultyList: List<SysDictVo>? = null//难度选项
    var textBookList: List<SysDictVo>? = null //教材下来数据
}