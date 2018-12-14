package com.xlf.xsrt.work.base

import com.xlf.xsrt.work.entry.BaseEntry

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestService {
    @FormUrlEncoded
    @POST("appLogin/userRegister")
    fun register(@FieldMap map: Map<String, String>): Observable<BaseEntry>
}