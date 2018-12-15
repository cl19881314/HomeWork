package com.xlf.xsrt.work.teacher.mylayout.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.widget.xxxrecycler.XXXAdapter
import kotlinx.android.synthetic.main.item_subject_layout.view.*

class MyArrangeAdapter : XXXAdapter<MyArrangeAdapter.ArrangeHolder>() {
    override fun getRealItemCount(): Int {
        return 10
    }

    override fun onRealCreateViewHolder(parent: ViewGroup?, viewType: Int): ArrangeHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_subject_layout, parent, false)
        return ArrangeHolder(view)
    }

    override fun onRealBindViewHolder(holder: ArrangeHolder?, position: Int) {
        holder!!.bindTo(position)
    }


    inner class ArrangeHolder(var view : View) : RecyclerView.ViewHolder(view){
        fun bindTo(position : Int){
            view.showDetailTxt.setOnClickListener {

            }
        }
    }
}