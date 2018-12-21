package com.xlf.xsrt.work.student.adapter

import android.content.Intent
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.detail.QuestionDetailActivity
import com.xlf.xsrt.work.student.StudentToAnswerActivity
import com.xlf.xsrt.work.student.bean.HomeworkStuVo
import com.xlf.xsrt.work.teacher.answer.CommentDetailActivity
import kotlinx.android.synthetic.main.xsrt_item_homework_student.view.*

class StudentAdapter : BaseRcyAdapter<HomeworkStuVo>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_homework_student
    }

    override fun setItemContent(itemView: View, bean: HomeworkStuVo, positon: Int, listener: ItemChildViewClickListener?) {
        itemView.showDetailTxt.visibility = View.VISIBLE
        if (bean.state == 0) {
            itemView.showDetailTxt.text = "待提交"
            itemView.showDetailTxt.setTextColor(itemView.resources.getColor(R.color.xsrt_read_bg_color))
        } else if (bean.state == 1) {
            itemView.showDetailTxt.text = "待批阅"
            itemView.showDetailTxt.setTextColor(itemView.resources.getColor(R.color.xsrt_yellow_bg_color))
        } else {
            itemView.showDetailTxt.visibility = View.GONE
            itemView.btn_checkMark_item_stu.visibility = View.VISIBLE
        }

        itemView.subject_item_student.text = bean.subjectName
        itemView.homeWorkNameTxt.text = bean.homeworkName

        itemView.btn_checkMark_item_stu.setOnClickListener {
            var intent = Intent(itemView.context, CommentDetailActivity::class.java)
            intent.putExtra("stuAnswerId", bean.stuAnswerId)
            itemView.context.startActivity(intent)
        }

        itemView.answerLayout.setOnClickListener {
            var intent = Intent()
            if (bean.state == 0) {
                intent.setClass(itemView.context, StudentToAnswerActivity::class.java)
                intent.putExtra("title", bean.homeworkName)
                intent.putExtra("groupId", bean.homeworkId)
                intent.putParcelableArrayListExtra("data", bean.homeworkBaseList!!)
            } else {
                intent.setClass(itemView.context, QuestionDetailActivity::class.java)
                intent.putExtra("title", bean.homeworkName)
                intent.putStringArrayListExtra("urlList", bean.analysisUrlList)
                intent.putExtra("showTodo", false)
            }
            itemView.context.startActivity(intent)
        }
    }
}