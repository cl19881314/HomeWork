package com.xlf.xsrt.work.student.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 日历界面作业数据
 */
class HomeworkStuVo {
    var homeworkId = -1
    var createTile = ""
    var timeState = 0 //某天作业状态0，有未提交，1全部提交
    var homeworkName = ""
    var subjectName = ""
    var stuAnswerId = -1
    var createTime = ""
    var state = 0 //状态 0待提交 1待批阅 2已批阅
    var analysisUrlList: ArrayList<String>? = null
    var homeworkBaseList: ArrayList<HomeworkBaseVo>? = null

    class HomeworkBaseVo() : Parcelable{
        var homeworkDetailUrl = ""
        var homeworkContentUrl = ""
        var homeworkId = 0
        var optionCount = 0

        constructor(parcel: Parcel) : this() {
            homeworkDetailUrl = parcel.readString()
            homeworkContentUrl = parcel.readString()
            homeworkId = parcel.readInt()
            optionCount = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(homeworkDetailUrl)
            parcel.writeString(homeworkContentUrl)
            parcel.writeInt(homeworkId)
            parcel.writeInt(optionCount)
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
}