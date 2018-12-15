package com.xlf.xsrt.work.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter

abstract class BaseRcyAdapter<T> : XXXAdapter<BaseRcyHolder>() {
    private var mData: MutableList<T>? = mutableListOf()



    fun addData(data: MutableList<T>) {
        addData(data, false)
    }

    fun addData(data: MutableList<T>, clear: Boolean) {
        if (mData != null) {
            if (clear) {
                mData!!.clear()
                mData!!.addAll(data)
            } else {
                mData!!.addAll(data)
            }
        }
        notifyDataSetChanged()
    }

    override fun getRealItemCount(): Int {
        return if (mData == null) {
            0
        } else {
            mData!!.size
        }
    }

    override fun onRealCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseRcyHolder {
        return BaseRcyHolder(LayoutInflater.from(parent!!.context).inflate(initLayoutId(viewType), parent, false))
    }

    override fun onRealBindViewHolder(holder: BaseRcyHolder?, position: Int) {
        setItemContent(holder!!.itemView, mData!![position], position)
    }

    abstract fun initLayoutId(viewType: Int): Int
    abstract fun setItemContent(itemView: View, bean: T, positon: Int)
}