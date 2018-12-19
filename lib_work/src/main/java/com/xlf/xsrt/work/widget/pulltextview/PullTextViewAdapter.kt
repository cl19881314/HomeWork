package com.xlf.xsrt.work.widget.pulltextview

import android.graphics.Color
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import kotlinx.android.synthetic.main.xsrt_item_popwindow.view.*

class PullTextViewAdapter : BaseRcyAdapter<PullBean>() {

    override fun initLayoutId(viewType: Int): Int {
        return R.layout.xsrt_item_popwindow
    }

    override fun setItemContent(itemView: View, bean: PullBean, positon: Int) {
        itemView.tv_name_popwindow.text = bean.content
        if (bean.selected) {
            itemView.tv_name_popwindow.setTextColor(Color.parseColor("#00724c"))
            itemView.iv_selected_popwindow.visibility = View.VISIBLE
        } else {
            itemView.tv_name_popwindow.setTextColor(Color.parseColor("#333333"))
            itemView.iv_selected_popwindow.visibility = View.GONE
        }
    }


    fun setItemSelected(positon: Int) {
        for (i in 0 until mData.size) {
            mData[i].selected = false
        }
        mData[positon].selected = true
        notifyDataSetChanged()
    }
}