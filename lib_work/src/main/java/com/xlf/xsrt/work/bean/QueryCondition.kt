package com.xlf.xsrt.work.bean

/**
 * 封装组页面查询条件
 */
class QueryCondition {
    var userId = ""
    var textbookId = ""
    var directoryId = ""
    var chapterId = ""
    var baseFlag = "1" //默认是基础题库  0 我的收藏 1 基础题库
    var difficultyId = ""
    var page = 0
    var groupedHomeworkId = 0
}