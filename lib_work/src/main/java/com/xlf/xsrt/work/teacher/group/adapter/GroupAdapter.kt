package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.entry.HomeworkBaseVo
import kotlinx.android.synthetic.main.item_subject_layout.view.*

class GroupAdapter : BaseRcyAdapter<HomeworkBaseVo>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.item_subject_layout
    }

    override fun setItemContent(itemView: View, bean: HomeworkBaseVo, positon: Int) {
        itemView.isCollected.isChecked = bean.collectFlag == 1
        itemView.subjectNumTxt.text = "编号${bean.homeworkNo}"
    }


}