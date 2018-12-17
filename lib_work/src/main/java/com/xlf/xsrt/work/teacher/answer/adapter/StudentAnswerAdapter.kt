package com.xlf.xsrt.work.teacher.answer.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.teacher.answer.CommentDetailActivity
import com.xlf.xsrt.work.teacher.answer.StudentAnswerDetailActivity
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter
import kotlinx.android.synthetic.main.xsrt_item_student_answer_layout.view.*

class StudentAnswerAdapter : XXXAdapter<StudentAnswerAdapter.AnswerHolder>() {
    override fun getRealItemCount(): Int {
        return 10
    }

    override fun onRealCreateViewHolder(parent: ViewGroup?, viewType: Int): AnswerHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.xsrt_item_student_answer_layout, parent, false)
        return AnswerHolder(view)
    }

    override fun onRealBindViewHolder(holder: AnswerHolder?, position: Int) {
        holder!!.bindTo(position)
    }


    inner class AnswerHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(postion: Int) {
            view.toDoStausTxt.setOnClickListener {
                var intent = Intent(view.context, StudentAnswerDetailActivity::class.java)
                view.context.startActivity(intent)
            }
            view.endStatusTxt.setOnClickListener {
                var intent = Intent(view.context, CommentDetailActivity::class.java)
                view.context.startActivity(intent)
            }
        }
    }
}