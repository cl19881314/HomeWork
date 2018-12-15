package com.xlf.xsrt.work.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.xlf.xsrt.work.R

class PullSpinner : FrameLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initRootView()
    }

    private fun initRootView() {
        val rooView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        addView(rooView)
    }
}