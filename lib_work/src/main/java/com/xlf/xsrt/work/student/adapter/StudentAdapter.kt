package com.xlf.xsrt.work.student.adapter

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.student.ReadOverActivity
import com.xlf.xsrt.work.student.bean.StuHomwork
import kotlinx.android.synthetic.main.xsrt_item_homework_student.view.*

class StudentAdapter : BaseRcyAdapter<StuHomwork>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_homework_student
    }

    override fun setItemContent(itemView: View, bean: StuHomwork, positon: Int) {
        itemView.btn_checkMark_item_stu.setOnClickListener {
            ReadOverActivity.start(itemView.context, bean.stuAnswerId!!)
        }
    }
}