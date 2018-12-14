package com.cfwu.lib_work.entry
/**
 * 作业题
 */
class HomeworkBaseVo {
    var homeworkId: Int? = null
    var homeworkNo: Int? = null
    var homeworkContentUrl: String? = null//题内容的url
    var homeworkDetailUrl: String? = null//题详情的url
    var collectFlag: Int? = null //是否被收藏 1是 0 否
    var addFlag: Int? = null //是否被添加 1已添加 0 未添加
}