package com.xlf.xsrt.work.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
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
    private var mBackImg: ImageView ? = null
    private var mTitleTxt: TextView ? = null
    private var mRightTxt: TextView ?= null

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
        val view = LayoutInflater.from(context).inflate(R.layout.view_titlebar, this)
        mBackImg = view.findViewById<View>(R.id.back_img) as ImageView
        mTitleTxt = view.findViewById<View>(R.id.title) as TextView
        mRightTxt = view.findViewById<View>(R.id.rightText) as TextView
        mBackImg!!.setOnClickListener {
            listener?.leftImgClick()
        }
        RxView.clicks(mRightTxt!!).throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    listener?.rightTextClick()
                }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val titleString = typedArray.getString(R.styleable.TitleBar_titleTxt)
        val rightString = typedArray.getString(R.styleable.TitleBar_rightTxt)
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

}
