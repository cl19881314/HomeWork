package com.xlf.xsrt.work.base

import com.xlf.xsrt.work.bean.*
import com.xlf.xsrt.work.student.bean.AnalysisDataBean
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.student.bean.StuHomwork
import com.xlf.xsrt.work.teacher.answer.bean.StuAnswerDetailBean
import com.xlf.xsrt.work.teacher.answer.bean.TeacherCommentBean
import com.xlf.xsrt.work.teacher.group.bean.AddRespondeBean
import com.xlf.xsrt.work.teacher.group.bean.GroupHomeWorkBean

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestService {
    @FormUrlEncoded
    @POST("appLogin/userRegister")
    fun register(@FieldMap map: Map<String, String>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("appLogin/queryToken")
    fun login(@FieldMap map: Map<String, String>): Observable<LoginBean>

    @FormUrlEncoded
    @POST("teacherManage/groupHomework")
    fun queryGroupData(@FieldMap map: Map<String, String>): Observable<GroupeEntry>

    @FormUrlEncoded
    @POST("teacherManage/queryHomework")
    fun queryHomeworkData(@FieldMap parame: HashMap<String, String>): Observable<GroupeEntry>

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
    @POST("teacherManage/getStuHomework")
    fun getStudentAnswerData(@FieldMap parameter: HashMap<String, Any>): Observable<StudentAnswerBean>

    @FormUrlEncoded
    @POST("teacherManage/getComment")
    fun getTeacherCommentData(@FieldMap parameter: HashMap<String, Int>): Observable<TeacherCommentBean>

    @FormUrlEncoded
    @POST("teacherManage/setComment")
    fun setTeacherCommentData(@FieldMap parameter: HashMap<String, Any>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("teacherManage/stuAnswerDetail")
    fun getStuAnswerDetailData(@FieldMap parameter: HashMap<String, Any>): Observable<StuAnswerDetailBean>

    @FormUrlEncoded
    @POST("studentManage/stuIndex")
    fun getStuHomeworkByDate(@FieldMap parameter: HashMap<String, String>): Observable<StuHomwork>

    @FormUrlEncoded
    @POST("studentManage/stuIndexStateCurrentTime")
    fun getHomeworkByDay(@FieldMap parameter: HashMap<String, String>): Observable<StuHomwork>

    @FormUrlEncoded
    @POST("teacherManage/addOrCancel")
    fun addOrCancleHomework(@FieldMap parameter: HashMap<String, String>): Observable<AddRespondeBean>

    @FormUrlEncoded
    @POST("teacherManage/queryGroupedHomework")
    fun querySelectHomework(@FieldMap parameter: HashMap<String, String>): Observable<GroupHomeWorkBean>

    @FormUrlEncoded
    @POST("teacherManage/publishHomeWork")
    fun pushHomeWork(@FieldMap parameter: HashMap<String, String>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("teacherManage/collectOrCancel")
    fun collectOrCancel(@FieldMap parameter: HashMap<String, String>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("studentManage/submitHomework")
    fun submitStudentAnswer(@FieldMap parameter: HashMap<String, Any>): Observable<BaseEntry>

    @FormUrlEncoded
    @POST("studentManage/getAnalysisPages")
    fun getAnalyisesData(@FieldMap parameter: HashMap<String, Any>): Observable<AnalysisDataBean>

}
