package com.xlf.xsrt.work.teacher.group.adapter

import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.bean.SysDictVo
import kotlinx.android.synthetic.main.xsrt_item_screen_popwindow.view.*

class ScreenPopWindowAdapter : BaseRcyAdapter<SysDictVo>() {
    var posSelected = -1
    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_screen_popwindow
    }

    override fun setItemContent(itemView: View, bean: SysDictVo, positon: Int, listener: ItemChildViewClickListener?) {
        itemView.ck_name_screen_popwindow.text = "${bean.sysDictName}"
        itemView.ck_name_screen_popwindow.isChecked = bean.isSelected
    }

    fun unCheckedAll() {
        for (item in mData) {
            item.isSelected = false
        }
        posSelected = -1
        notifyDataSetChanged()
    }

    fun setIitemChecked(positon: Int) {
        unCheckedAll()
        posSelected = positon
        mData[positon].isSelected = true
        notifyDataSetChanged()
    }
}