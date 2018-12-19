package com.xlf.xsrt.work.teacher.answer.bean

import com.xlf.xsrt.work.entry.BaseEntry

/**
 * @author Chenhong
 * @date 2018/12/19.
 * @des
 */
class StuAnswerDetailBean : BaseEntry() {
    var question: QuestionVo? = null
    class QuestionVo {
        var questionContent = ""
        var itemCode = ""
        var correctAnswer = ""
        var analysisContent = ""
        var optionList: ArrayList<QuestionVo2>? = null
    }

    class QuestionVo2 {
        var optionNo = ""
        var optionContent = ""
    }
}