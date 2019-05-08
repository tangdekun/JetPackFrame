package com.djt.study.demo.data.source.http

import com.djt.study.demo.data.source.HttpDataSource
import com.djt.study.demo.data.source.http.service.DemoApiService
import com.djt.study.demo.entity.DemoEntity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import com.djt.base.http.BaseResponse

import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Created by goldze on 2019/3/26.
 */
class HttpDataSourceImpl private constructor(private val apiService: DemoApiService) : HttpDataSource {

    @Override
    fun simulationLogin(): Observable<Object> {
        return Observable.just(Object()).delay(3, TimeUnit.SECONDS) //延迟3秒
    }

    @Override
    fun simulationLoadMore(): Observable<DemoEntity> {
        return Observable.create(object : ObservableOnSubscribe<DemoEntity>() {
            @Override
            @Throws(Exception::class)
            fun subscribe(observableEmitter: ObservableEmitter<DemoEntity>) {
                val entity = DemoEntity()
                val itemsEntities = ArrayList()
                //模拟一部分假数据
                for (i in 0..9) {
                    val item = DemoEntity.ItemsEntity()
                    item.setId(-1)
                    item.setName("模拟条目")
                    itemsEntities.add(item)
                }
                entity.setItems(itemsEntities)
                observableEmitter.onNext(entity)
            }
        }).delay(3, TimeUnit.SECONDS) //延迟3秒
    }

    @Override
    fun demoGet(): Observable<BaseResponse<DemoEntity>> {
        return apiService.demoGet()
    }

    @Override
    fun demoPost(catalog: String): Observable<BaseResponse<DemoEntity>> {
        return apiService.demoPost(catalog)
    }

    companion object {
        @Volatile
        private var INSTANCE: HttpDataSourceImpl? = null

        fun getInstance(apiService: DemoApiService): HttpDataSourceImpl? {
            if (INSTANCE == null) {
                synchronized(HttpDataSourceImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = HttpDataSourceImpl(apiService)
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
