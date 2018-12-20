package com.xlf.xsrt.work.widget.pulltextview

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseRcyAdapter
import com.xlf.xsrt.work.utils.ScreenUtil
import kotlinx.android.synthetic.main.xsrt_layout_popwindow_group.view.*


class PullTextView : TextView {
    private var mListener: PullListItemListener? = null
    private var mPopWindow: PopupWindow? = null
    var isChecked = true
    private var mDrawableId = -1
    private var mShowDefaultDrawable = true
    private var mIsBold = true

    private val mAdapter by lazy {
        PullTextViewAdapter()
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
        initPopWindow()
        initListener()
    }

    private fun setup(context: Context?, attrs: AttributeSet?) {
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.XsrtPullTxt)
        mDrawableId = typedArray.getResourceId(R.styleable.XsrtPullTxt_xsrtRightDrawable, -1)
        mIsBold = typedArray.getBoolean(R.styleable.XsrtPullTxt_xsrtIsBlod, true)
        if (mDrawableId != -1) {
            mShowDefaultDrawable = false
        }
        typedArray.recycle()
        if (mIsBold) {
            //加粗
            this.paint.isFakeBoldText = true
        }
        //设置drawpadding
        this.compoundDrawablePadding = ScreenUtil.dipToPx(context, 4).toInt()
    }

    private fun initListener() {
        mAdapter.setOnItemClickListener(object : BaseRcyAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                if (!mAdapter.getData()[position].selected) {
                    mAdapter.setItemSelected(position)
                }
                mListener?.onItemClick(mAdapter.getData()[position], position)
                hidePop()
            }
        })
    }

    private fun initPopWindow() {
        val windowView = LayoutInflater.from(context).inflate(R.layout.xsrt_layout_popwindow_group, null)
        windowView.rcy_popwindow.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        windowView.rcy_popwindow.adapter = mAdapter
        windowView.pull_text_root.setOnClickListener { hidePop() }
        mPopWindow = PopupWindow(windowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mPopWindow?.isOutsideTouchable = false
        mPopWindow?.isFocusable = false
    }

    fun updateData(data: MutableList<PullBean>, clear: Boolean) {
        mAdapter.addData(data, clear)
    }

    fun clearData() {
        mAdapter.clearData()
    }

    fun showPop() {
        isChecked = false
        if (mShowDefaultDrawable) {
            mDrawableId = R.drawable.xsrt_icon_spinner_up
        }
        val drawable = ResourcesCompat.getDrawable(resources, mDrawableId, null)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)//必须设置图片大小，否则不显示
        this.setCompoundDrawables(null, null, drawable, null)
        showPopWindow()
    }

    fun hidePop() {
        isChecked = true
        if (mShowDefaultDrawable) {
            mDrawableId = R.drawable.xsrt_icon_spinner_down
        }
        val drawable = ResourcesCompat.getDrawable(resources, mDrawableId, null)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)//必须设置图片大小，否则不显示
        this.setCompoundDrawables(null, null, drawable, null)
        mPopWindow?.dismiss()
    }

    fun isPopWindowShow(): Boolean {
        return mPopWindow != null && mPopWindow!!.isShowing
    }

    private fun showPopWindow() {
        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow!!.showAsDropDown(this, 0, ScreenUtil.dipToPx(context, 1).toInt())
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
            mPopWindow!!.showAsDropDown(this, location[0], location[1] + this.height + ScreenUtil.dipToPx(context, 1).toInt())
        }
    }

    fun setItemClickListener(listener: PullListItemListener) {
        this.mListener = listener
    }

    open interface PullListItemListener {
        fun onItemClick(bean: PullBean, position: Int)
    }

}