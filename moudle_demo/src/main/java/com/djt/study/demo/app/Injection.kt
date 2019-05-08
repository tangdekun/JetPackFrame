package com.djt.study.demo.app

import com.djt.study.demo.data.DemoRepository
import com.djt.study.demo.data.source.HttpDataSource
import com.djt.study.demo.data.source.LocalDataSource
import com.djt.study.demo.data.source.http.HttpDataSourceImpl
import com.djt.study.demo.data.source.http.service.DemoApiService
import com.djt.study.demo.data.source.local.LocalDataSourceImpl
import com.djt.study.demo.utils.RetrofitClient


/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 * Created by goldze on 2019/3/26.
 */
object Injection {
    fun provideDemoRepository(): DemoRepository {
        //网络API服务
        val apiService = RetrofitClient.getInstance().create(DemoApiService::class.java)
        //网络数据源
        val httpDataSource = HttpDataSourceImpl.getInstance(apiService)
        //本地数据源
        val localDataSource = LocalDataSourceImpl.getInstance()
        //两条分支组成一个数据仓库
        return DemoRepository.getInstance(httpDataSource, localDataSource)
    }
}
