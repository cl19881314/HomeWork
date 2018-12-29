package com.xlf.xsrt.work.http

import android.text.TextUtils
import android.util.Log
import com.xlf.xsrt.work.bean.*
import com.xlf.xsrt.work.student.bean.AnalysisDataBean
import com.xlf.xsrt.work.teacher.answer.bean.StudentAnswerBean
import com.xlf.xsrt.work.student.bean.StuHomwork
import com.xlf.xsrt.work.teacher.answer.bean.TeacherCommentBean
import com.xlf.xsrt.work.teacher.group.bean.AddRespondeBean
import com.xlf.xsrt.work.teacher.group.bean.GroupHomeWorkBean
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class RequestApi {
    private val mService: RequestService

    init {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            if (message == null || !ServiceApi.DEBUG)
                return@HttpLoggingInterceptor
            Log.d("retrofit", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(65, TimeUnit.SECONDS)
                .writeTimeout(65, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(ServiceApi.IP)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mService = retrofit.create(RequestService::class.java)
    }

    companion object {
        private var mInstance: RequestApi? = null
        fun getInstance(): RequestApi {
            if (mInstance == null) {
                synchronized(RequestApi::class.java) {
                    if (mInstance == null) {
                        mInstance = RequestApi()
                    }
                }
            }
            return mInstance!!
        }
    }

    /**
     * 测试用到的登陆接口
     */
    fun login(user: String, pwd: String): Observable<LoginBean> {
        var parame = HashMap<String, String>()
        parame["username"] = user
        parame["password"] = pwd
        return mService.login(parame)
    }

    /**
     * 初始查询组作业页面数据
     */
    fun queryGroupData(userId: Int): Observable<GroupeEntry> {
        var parame = HashMap<String, String>()
        parame["userId"] = "$userId"
        return mService.queryGroupData(parame)
    }

    /**
     * 条件查询组作业数据
     */
//    fun queryHomeworkData(userId: Int, textbookId: Int, directoryId: Int, chapterId: Int, baseFlag: Int, difficultyId: Int, page: Int): Observable<GroupeEntry> {
    fun queryHomeworkData(userId: String, textbookId: String, directoryId: String, chapterId: String, baseFlag: String, difficultyId: String, page: Int, groupedHomeworkId: Int): Observable<GroupeEntry> {
        var parame = HashMap<String, String>()
        parame["userId"] = userId
        parame["textbookId"] = textbookId
        parame["directoryId"] = directoryId
        parame["chapterId"] = chapterId
        parame["baseFlag"] = baseFlag
        parame["difficultyId"] = difficultyId
        parame["page"] = page.toString()
        parame["groupedHomeworkId"] = groupedHomeworkId.toString()

        return mService.queryHomeworkData(parame)
    }

    /**
     * 查询教师或者学生信息
     */
    fun queryUserInfo(token: String): Observable<UserInfo> {
        var parame = HashMap<String, String>()
        parame["userInfo"] = "$token"
        return mService.queryUserInfo(parame)
    }

    /**
     * 获取教师端我的布置数据
     */
    fun getMyArrangeData(userId: Int, groupedHomeworkId: Int): Observable<MyArrangeBean> {
        var parameter = HashMap<String, Int>()
        parameter["userId"] = userId
        if (groupedHomeworkId != -1) {
            parameter["groupedHomeworkId"] = groupedHomeworkId
        }
        return mService.getMyArrangeData(parameter)
    }

    /**
     *  删除教师端预约布置的作业
     */
    fun deleteAppointmentWork(userId: Int, groupedHomeworkId: Int): Observable<BaseEntry> {
        var parameter = HashMap<String, Int>()
        parameter["userId"] = userId
        parameter["groupedHomeworkId"] = groupedHomeworkId
        return mService.deleteAppointmentWork(parameter)
    }

    /**
     *  教师端 获取学生作业
     */
    fun getStudentAnswerData(userId: Int, classId: Int, createTime: String, homeworkId: Int): Observable<StudentAnswerBean> {
        var parameter = HashMap<String, Any>()
        parameter["userId"] = userId
        if (classId != -1) {
            parameter["classId"] = classId
        }
        if (!TextUtils.isEmpty(createTime)) {
            parameter["createTime"] = createTime
        }
        if (homeworkId != -1) {
            parameter["homeworkId"] = homeworkId
        }
        return mService.getStudentAnswerData(parameter)
    }

    /**
     * 教师批阅内容
     */
    fun getTeacherCommentData(stuAnswerId: Int): Observable<TeacherCommentBean> {
        var parameter = HashMap<String, Int>()
        parameter["stuAnswerId"] = stuAnswerId
        return mService.getTeacherCommentData(parameter)
    }

    /**
     * 添加教师批阅
     */
    fun setTeacherCommentData(stuAnswerId: Int, comment: String): Observable<BaseEntry> {
        var parameter = HashMap<String, Any>()
        parameter["stuAnswerId"] = stuAnswerId
        parameter["comment"] = comment
        return mService.setTeacherCommentData(parameter)
    }

    /**
     * 学生端 获取日历首页数据
     */
    fun getStuHomeworkByDate(userId: Int, createTime: String): Observable<StuHomwork> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = userId.toString()
        parameter["createTime"] = createTime
        return mService.getStuHomeworkByDate(parameter)
    }

    /**
     * 学生端 获取日历当天数据
     */
    fun getHomeworkByDay(userId: Int, createTime: String): Observable<StuHomwork> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = userId.toString()
        parameter["createTime"] = createTime
        return mService.getHomeworkByDay(parameter)
    }

    /**
     * 教师端 添加 移除作业
     * @param teacherId
     * @param addFlag
     * @param groupedHomework
     * @param homeworkIds
     *
     */
    fun addOrCancleHomework(teacherId: Int, addFlag: Int, groupedHomework: Int, homeworkIds: String): Observable<AddRespondeBean> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = teacherId.toString()
        parameter["addFlag"] = addFlag.toString()
        parameter["groupedHomeworkId"] = groupedHomework.toString()
        parameter["homeworkIds"] = homeworkIds
        return mService.addOrCancleHomework(parameter)

    }

    /**
     * 查询已选作业
     */
    fun querySelectHomework(userId: Int, groupHomeworkId: Int): Observable<GroupHomeWorkBean> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = userId.toString()
        parameter["groupedHomeworkId"] = groupHomeworkId.toString()
        return mService.querySelectHomework(parameter)
    }

    /**
     * 提交已选作业
     */
    fun pushHomeWork(teacherId: Int, appointmentTime: String, groupedHomeworkId: Int, groupedHomeworkId1: Int, homeworkName: String): Observable<BaseEntry> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = teacherId.toString()
        parameter["appointmentTime"] = appointmentTime
        parameter["groupedHomeworkId"] = groupedHomeworkId.toString()
        parameter["homeworkName"] = homeworkName
        return mService.pushHomeWork(parameter)
    }

    /**
     * 收藏或者取消收藏
     */
    fun collectOrCancel(userId: Int, homeworkId: Int?, collectFlag: Int?): Observable<BaseEntry> {
        var parameter = HashMap<String, String>()
        parameter["userId"] = userId.toString()
        parameter["homeworkId"] = homeworkId.toString()
        parameter["collectFlag"] = collectFlag.toString()
        return mService.collectOrCancel(parameter)
    }

    /**
     * 学生端 提交答案
     */
    fun submitStudentAnswer(userId: Int, groupId: Int, studentAnswers: String): Observable<BaseEntry> {
        var parameter = HashMap<String, Any>()
        parameter["userId"] = userId
        parameter["groupedHomeworkId"] = groupId
        parameter["studentAnswers"] = studentAnswers
        return mService.submitStudentAnswer(parameter)
    }

    /**
     * 学生答题后提交成功后要重新请求获取解析页面数据
     */
    fun getAnaysyPage(userId: Int, homeworkId: Int): Observable<AnalysisDataBean> {
        var parameter = HashMap<String, Any>()
        parameter["userId"] = userId
        parameter["homeworkId"] = homeworkId
        return mService.getAnalyisesData(parameter)
    }

}