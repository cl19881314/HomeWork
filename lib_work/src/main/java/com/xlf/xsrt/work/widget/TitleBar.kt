package com.xlf.xsrt.work.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.jakewharton.rxbinding2.view.RxView
import com.xlf.xsrt.work.R

import java.util.concurrent.TimeUnit


class TitleBar : RelativeLayout {
    private var mTitleBarLayout: RelativeLayout? = null
    private var mBackImg: ImageView? = null
    private var mTitleTxt: TextView? = null
    private var mRightTxt: TextView? = null

    private var listener: TitleBarClickListener? = null

    interface TitleBarClickListener {
        fun leftImgClick()
        fun rightTextClick()
    }

    fun setTitleBarClickListener(listener: TitleBarClickListener) {
        this.listener = listener
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val view = LayoutInflater.from(context).inflate(R.layout.xsrt_view_titlebar, this)
        mTitleBarLayout = view.findViewById(R.id.title_bar)
        mBackImg = view.findViewById(R.id.iv_back_basetitle)
        mTitleTxt = view.findViewById(R.id.tv_title_basetitle)
        mRightTxt = view.findViewById(R.id.tv_right_basetitle)
        mBackImg!!.setOnClickListener {
            listener?.leftImgClick()
        }
        RxView.clicks(mRightTxt!!).throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    listener?.rightTextClick()
                }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.XsrtTitleBar)
        val titleString = typedArray.getString(R.styleable.XsrtTitleBar_xsrtTitleTxt)
        val rightString = typedArray.getString(R.styleable.XsrtTitleBar_xsrtRightTxt)
        if (!TextUtils.isEmpty(rightString)) {
            mRightTxt!!.visibility = View.VISIBLE
            mRightTxt!!.text = rightString
        } else {
            mRightTxt!!.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(titleString)) {
            mTitleTxt!!.text = titleString
        }
        typedArray.recycle()
    }

    fun getTitleBarLayout(): RelativeLayout? {
        return mTitleBarLayout
    }

    fun getBackImageView(): ImageView? {
        return mBackImg
    }

    fun setTitleTxt(txt : String){
        mTitleTxt?.text = txt
    }

    fun setRightTextClor(color : Int){
        mRightTxt?.setTextColor(color)
    }
}
