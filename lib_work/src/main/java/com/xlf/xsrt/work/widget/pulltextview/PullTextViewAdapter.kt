package com.xlf.xsrt.work.widget.pulltextview

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.teacher.group.bean.SysDictVo
import kotlinx.android.synthetic.main.xsrt_item_popwindow.view.*

class PullTextViewAdapter : BaseRcyAdapter<PullBean>() {

    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_popwindow
    }

    override fun setItemContent(itemView: View, bean: PullBean, positon: Int) {
        itemView.tv_name_popwindow.text = "${bean.content}"
    }
}