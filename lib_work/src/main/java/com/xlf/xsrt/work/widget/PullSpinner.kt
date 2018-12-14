package com.xlf.xsrt.work.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import kotlinx.android.synthetic.main.item_spinner_rcy.view.*
import kotlinx.android.synthetic.main.spinner_layout.view.*

class PullSpinner : View {
    private var mTxtData: MutableList<String> = mutableListOf()
    private var mAdapter: BaseRcyAdapter<String>? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initRootView()
    }

    private fun initRootView() {
        val rooView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        rooView.rcy_spinner.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter = SpinnerAdapter(mTxtData)
        rooView.rcy_spinner.adapter = mAdapter
        rooView.ibtn_spinner.setOnClickListener {
            toggleRcyView()
        }
    }


    private fun toggleRcyView() {
        if (rcy_spinner.isShown) {
            rcy_spinner.visibility = View.GONE
        } else {
            rcy_spinner.visibility = View.VISIBLE
        }
    }

    fun setSpinnerData(data: MutableList<String>) {
        mAdapter!!.addData(data, true)
    }

    inner class SpinnerAdapter(data: MutableList<String>) : BaseRcyAdapter<String>(data) {
        override fun setItemContent(itemView: View, string: String, positon: Int) {
            itemView.tv_content_item_spinner.text = string
            itemView.setOnClickListener {
                tv_spinner.text = string
                rcy_spinner.visibility = View.GONE
            }
        }

        override fun initLayoutId(): Int {
            return R.layout.item_spinner_rcy
        }


    }
}