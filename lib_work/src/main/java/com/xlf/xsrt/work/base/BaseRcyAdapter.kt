package com.xlf.xsrt.work.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter

abstract class BaseRcyAdapter<T> : XXXAdapter<BaseRcyHolder>() {
    open var mData: MutableList<T> = mutableListOf()
    private var mItemListener: ItemClickListener? = null
    private var mItemChildListener: ItemChildViewClickListener? = null
    fun addData(data: MutableList<T>) {
        addData(data, false)
    }

    fun addData(data: MutableList<T>, clear: Boolean) {

        if (clear) {
            mData.clear()
            mData.addAll(data)
        } else {
            mData.addAll(data)
        }

        notifyDataSetChanged()
    }

    fun replace(position: Int, bean: T) {
        mData.removeAt(position)
        mData.add(position, bean)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

    fun getData(): MutableList<T> {
        return mData
    }

    fun removeData(position: Int){
        mData.removeAt(position)
        notifyDataSetChanged()
    }

    fun getItemContent(position: Int): T? {
        return mData[position]
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
        holder?.itemView!!.setOnClickListener {
            mItemListener?.onItemClick(position)
        }
        setItemContent(holder!!.itemView, mData!![position], position, mItemChildListener)
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.mItemListener = listener
    }

    fun setOnItemChildViewClickListener(listener: ItemChildViewClickListener) {
        this.mItemChildListener = listener
    }

    abstract fun initLayoutId(viewType: Int): Int
    abstract fun setItemContent(itemView: View, bean: T, positon: Int, listener: ItemChildViewClickListener?)

    open interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    open interface ItemChildViewClickListener {
        fun onItemChildClick(childView: View, position: Int)
    }
}