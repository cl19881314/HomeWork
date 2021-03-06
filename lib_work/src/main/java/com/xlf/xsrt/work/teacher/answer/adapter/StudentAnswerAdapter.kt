package com.xlf.xsrt.work.teacher.answer.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.teacher.answer.CommentDetailActivity
import com.xlf.xsrt.work.detail.QuestionDetailActivity
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter
import kotlinx.android.synthetic.main.xsrt_item_student_answer_layout.view.*

class StudentAnswerAdapter : XXXAdapter<StudentAnswerAdapter.AnswerHolder>() {

    private var mData = ArrayList<StudentAnswerBean.StuAnswerVo>()
    override fun getRealItemCount(): Int {
        return mData.size
    }

    override fun onRealCreateViewHolder(parent: ViewGroup?, viewType: Int): AnswerHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.xsrt_item_student_answer_layout, parent, false)
        return AnswerHolder(view)
    }

    override fun onRealBindViewHolder(holder: AnswerHolder?, position: Int) {
        holder!!.bindTo(position)
    }

    fun setAnswerData(stuAnswerList: ArrayList<StudentAnswerBean.StuAnswerVo>?) {
        if (stuAnswerList == null || stuAnswerList.isEmpty()){
            return
        }
        mData.clear()
        mData.addAll(stuAnswerList)
        notifyDataSetChanged()
    }

    inner class AnswerHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(position: Int) {
            var bean = mData[position]
            view.nameTxt.paint.isFakeBoldText = true
            view.errorNumTxt.paint.isFakeBoldText = true
            view.allNumTxt.paint.isFakeBoldText = true

            view.nameTxt.text = bean.stuName
            view.errorNumTxt.text = bean.wrongCount.toString()
            view.allNumTxt.text = "/${bean.totalCount}"
            view.timeTxt.text = bean.answerTime
            view.toDoStausTxt.visibility = if (bean.state == 0) View.VISIBLE else View.GONE
            view.endStatusTxt.visibility = if (bean.state == 0) View.GONE else View.VISIBLE
            view.itemAnswerLayout.setOnClickListener {
                var intent = Intent(view.context, QuestionDetailActivity::class.java)
                intent.putExtra("stuAnswerId", bean.stuAnswerId)
                var title = if(TextUtils.isEmpty(bean.stuName)) " " else bean.stuName
                intent.putExtra("title", title)
                intent.putStringArrayListExtra("urlList", bean.homeworkDetailUrlList)
                intent.putExtra("showTodo", bean.state != 1)
                view.context.startActivity(intent)
            }
            view.endStatusTxt.setOnClickListener {
                var intent = Intent(view.context, CommentDetailActivity::class.java)
                intent.putExtra("stuAnswerId", bean.stuAnswerId)
                intent.putExtra("title", bean.stuName)
                view.context.startActivity(intent)
            }
        }
    }
}