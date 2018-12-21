package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.SysDictVo
import kotlinx.android.synthetic.main.xsrt_item_popwindow.view.*

class PopWindowAdapter : BaseRcyAdapter<SysDictVo>() {

    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_popwindow
    }

    override fun setItemContent(itemView: View, bean: SysDictVo, positon: Int, listener: ItemChildViewClickListener?) {
        itemView.tv_name_popwindow.text = "${bean.sysDictName}"
    }
}