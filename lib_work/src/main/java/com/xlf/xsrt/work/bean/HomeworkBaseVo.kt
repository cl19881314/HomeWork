package com.xlf.xsrt.work.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 作业题
 */
class HomeworkBaseVo() : Parcelable {
    var homeworkId: Int? = null
    //    var homeworkNo: Int? = null
    var homeworkContentUrl: String? = null//题内容的url
    var homeworkDetailUrl: String? = null//题详情的url
    var collectFlag: Int? = null //是否被收藏 1是 0 否
    var addFlag: Int? = null //是否被添加 1已添加 0 未添加
    var itemCode: Int? = null

    constructor(parcel: Parcel) : this() {
        homeworkId = parcel.readValue(Int::class.java.classLoader) as? Int
        homeworkContentUrl = parcel.readString()
        homeworkDetailUrl = parcel.readString()
        collectFlag = parcel.readValue(Int::class.java.classLoader) as? Int
        addFlag = parcel.readValue(Int::class.java.classLoader) as? Int
        itemCode = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(homeworkId)
        parcel.writeString(homeworkContentUrl)
        parcel.writeString(homeworkDetailUrl)
        parcel.writeValue(collectFlag)
        parcel.writeValue(addFlag)
        parcel.writeValue(itemCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeworkBaseVo> {
        override fun createFromParcel(parcel: Parcel): HomeworkBaseVo {
            return HomeworkBaseVo(parcel)
        }

        override fun newArray(size: Int): Array<HomeworkBaseVo?> {
            return arrayOfNulls(size)
        }
    }

}