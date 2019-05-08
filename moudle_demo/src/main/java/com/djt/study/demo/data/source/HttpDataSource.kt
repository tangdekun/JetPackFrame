package com.djt.study.demo.data.source

import com.djt.study.demo.entity.DemoEntity
import io.reactivex.Observable
import com.djt.base.http.BaseResponse

/**
 * Created by goldze on 2019/3/26.
 */
interface HttpDataSource {
    //模拟登录
    fun simulationLogin(): Observable<Object>

    //模拟上拉加载
    fun simulationLoadMore(): Observable<DemoEntity>

    fun demoGet(): Observable<BaseResponse<DemoEntity>>

    fun demoPost(catalog: String): Observable<BaseResponse<DemoEntity>>


}
