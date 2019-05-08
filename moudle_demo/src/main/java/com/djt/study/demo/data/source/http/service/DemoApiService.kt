package com.djt.study.demo.data.source.http.service

import com.djt.study.demo.entity.DemoEntity
import io.reactivex.Observable
import com.djt.base.http.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by goldze on 2017/6/15.
 */

interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    fun demoGet(): Observable<BaseResponse<DemoEntity>>

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    fun demoPost(@Field("catalog") catalog: String): Observable<BaseResponse<DemoEntity>>
}
