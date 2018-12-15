package com.xlf.xsrt.work.base

import android.util.Log
import com.xlf.xsrt.work.constant.ServiceApi
import com.xlf.xsrt.work.entry.GroupeEntry
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.HashMap

class RequestApi {
    private val mService: RequestService

    init {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            if (message == null)
                return@HttpLoggingInterceptor
            Log.d("tag", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(ServiceApi.IP + "/")
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
     * 查询组作业
     */
    fun queryGroupData(userId: Int): Observable<GroupeEntry> {
        var parame = HashMap<String, String>()
        parame["userId"] = "$userId"
        return mService.queryGroupData(parame)
    }

}