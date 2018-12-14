package com.xlf.xsrt.work.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRcyAdapter<T>(data: List<T>) : RecyclerView.Adapter<BaseRcyHolder>() {
    private var mData: List<T>? = null

    init {
        this.mData = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseRcyHolder {
        return BaseRcyHolder(LayoutInflater.from(p0.context).inflate(initLayoutId(), p0, false))
    }

    override fun getItemCount(): Int {
        return if (mData == null) {
            0
        } else {
            mData!!.size
        }
    }

    override fun onBindViewHolder(p0: BaseRcyHolder, p1: Int) {
        setItemContent(p0.itemView, mData!![p1], p1)
    }

    abstract fun initLayoutId(): Int
    abstract fun setItemContent(itemView: View, any: T, positon: Int)
}