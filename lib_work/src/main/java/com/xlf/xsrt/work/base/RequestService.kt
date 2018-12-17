package com.xlf.xsrt.work.base

import com.xlf.xsrt.work.entry.BaseEntry
import com.xlf.xsrt.work.entry.BaseStudentEntry
import com.xlf.xsrt.work.teacher.group.bean.GroupeEntry
import com.xlf.xsrt.work.teacher.group.bean.UserInfo

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestService {
    @FormUrlEncoded
    @POST("appLogin/userRegister")
    fun register(@FieldMap map: Map<String, String>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("teacherManage/groupHomework")
    fun queryGroupData(@FieldMap map: Map<String, String>): Observable<GroupeEntry>

    @FormUrlEncoded
    @POST("teacherManage/queryHomework")
    fun queryHomeworkData(@FieldMap parame: HashMap<String, String>): Observable<GroupeEntry>

    @FormUrlEncoded
    @POST("teacher/getComment")
    fun getTeacherComment(@FieldMap parame: HashMap<String, String>): Observable<BaseStudentEntry>

    @FormUrlEncoded
    @POST("appLogin/queryLogin")
    fun queryUserInfo(@FieldMap parame: HashMap<String, String>): Observable<UserInfo>
}
