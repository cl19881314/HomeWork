package com.xlf.xsrt.work.base

import com.xlf.xsrt.work.bean.BaseEntry
import com.xlf.xsrt.work.bean.BaseStudentEntry
import com.xlf.xsrt.work.bean.GroupeEntry
import com.xlf.xsrt.work.bean.MyArrangeBean
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.bean.UserInfo
import com.xlf.xsrt.work.student.bean.StuHomwork
import com.xlf.xsrt.work.teacher.answer.bean.StuAnswerDetailBean
import com.xlf.xsrt.work.teacher.answer.bean.TeacherCommentBean

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

    @FormUrlEncoded
    @POST("teacherManage/myPublishHomework")
    fun getMyArrangeData(@FieldMap parameter: HashMap<String, Int>): Observable<MyArrangeBean>

    @FormUrlEncoded
    @POST("teacherManage/deleteHomework")
    fun deleteAppointmentWork(@FieldMap parameter: HashMap<String, Int>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("teacher/getStuHomework")
    fun getStudentAnswerData(@FieldMap parameter: HashMap<String, Any>): Observable<StudentAnswerBean>

    @FormUrlEncoded
    @POST("teacher/getComment")
    fun getTeacherCommentData(@FieldMap parameter: HashMap<String, Int>): Observable<TeacherCommentBean>

    @FormUrlEncoded
    @POST("teacher/setComment")
    fun setTeacherCommentData(@FieldMap parameter: HashMap<String, Any>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("teacher/stuAnswerDetail")
    fun getStuAnswerDetailData(@FieldMap parameter: HashMap<String, Any>): Observable<StuAnswerDetailBean>

    @FormUrlEncoded
    @POST("student/stuIndex")
    fun getStuHomeworkByDate(@FieldMap parameter: HashMap<String, String>): Observable<StuHomwork>

    @FormUrlEncoded
    @POST("studentManage/stuIndexStateCurrentTime")
    fun getHomeworkByDay(@FieldMap parameter: HashMap<String, String>): Observable<StuHomwork>


}
