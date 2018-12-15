package com.xlf.xsrt.work.teacher.answer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class StudentAnswerDetailAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private var mData = ArrayList<Fragment>()

    fun setData(data: ArrayList<Fragment>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return mData[position]
    }

    override fun getCount(): Int {
        return mData.size
    }

}