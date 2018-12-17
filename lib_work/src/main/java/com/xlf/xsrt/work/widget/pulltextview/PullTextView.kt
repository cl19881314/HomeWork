package com.xlf.xsrt.work.widget.pulltextview

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.CheckedTextView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.utils.ScreenUtil
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_group.view.*

class PullTextView : CheckedTextView, View.OnClickListener {
    private var mListener: PullListItemListener? = null
    override fun onClick(v: View?) {
        toggle()
        if (isChecked) {
            showPopWindow()
        } else {
            mPopWindow?.dismiss()
        }
    }

    private var mPopWindow: PopupWindow? = null
    private var mAdapter: PullTextViewAdapter? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPopWindow()
        initListener()

    }

    private fun initListener() {
        setOnClickListener(this)
        mAdapter?.setOnItemClickListener(object : BaseRcyAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                mListener?.onItemClick(mAdapter?.getData()!![position], position)
            }
        })
    }

    private fun initPopWindow() {
        val windowView = LayoutInflater.from(context).inflate(R.layout.xsrt_layout_popwindow_group, null)
        mAdapter = PullTextViewAdapter()
        windowView.rcy_popwindow.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        windowView.rcy_popwindow.adapter = mAdapter
        mPopWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mPopWindow?.isOutsideTouchable = true
        mPopWindow?.isFocusable = false
    }

    fun updateData(data: MutableList<PullBean>, clear: Boolean) {
        mAdapter?.addData(data, clear)
    }

    fun setItemClickListener(listener: PullListItemListener) {
        this.mListener = listener
    }

    fun showPopWindow() {
        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow!!.showAsDropDown(this)
        } else {
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            if (Build.VERSION.SDK_INT == 25) {
                var tempheight = mPopWindow!!.height
                if (tempheight == WindowManager.LayoutParams.MATCH_PARENT || ScreenUtil.getScreenHeight(context) <= tempheight) {
                    mPopWindow!!.height = ScreenUtil.getScreenHeight(context) - location[1] - this.height;
                }
            } else {
                val visibleFrame = Rect()
                this.getGlobalVisibleRect(visibleFrame)
                val height = this.resources.displayMetrics.heightPixels - visibleFrame.bottom
                mPopWindow!!.height = height
            }
            mPopWindow!!.showAsDropDown(this, location[0], location[1] + this.height)
        }
    }

    open interface PullListItemListener {
        fun onItemClick(bean: PullBean, position: Int)
    }
}