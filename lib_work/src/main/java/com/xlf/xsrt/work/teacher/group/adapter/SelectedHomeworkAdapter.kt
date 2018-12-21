package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.HomeworkBaseVo

class SelectedHomeworkAdapter : BaseRcyAdapter<HomeworkBaseVo>() {
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_subject_layout

    }

    override fun setItemContent(itemView: View, bean: HomeworkBaseVo, positon: Int, listener: ItemChildViewClickListener?) {
    }
}