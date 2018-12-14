package com.xlf.xsrt.work.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.entry.SysDictVo
import kotlinx.android.synthetic.main.item_spinner_rcy.view.*

class PullSpinner : View {
    private var mTxtData: List<SysDictVo>? = mutableListOf()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initRootView()
    }

    private fun initRootView() {
        val rooView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)

    }

    inner class SpinnerAdapter(data: List<SysDictVo>) : BaseRcyAdapter<SysDictVo>(data) {
        override fun initLayoutId(): Int {
            return R.layout.item_spinner_rcy
        }

        override fun setItemContent(itemView: View, obj: SysDictVo, positon: Int) {
            itemView.tv_content_item_spinner.text=obj.sysDictName

        }


    }
}