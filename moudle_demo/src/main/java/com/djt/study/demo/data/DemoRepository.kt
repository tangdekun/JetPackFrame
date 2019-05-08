package com.djt.study.demo.data

import android.support.annotation.VisibleForTesting
import com.djt.study.demo.data.source.HttpDataSource
import com.djt.study.demo.data.source.LocalDataSource
import com.djt.study.demo.entity.DemoEntity
import io.reactivex.Observable
import com.djt.mvvm.base.BaseModel
import com.djt.base.http.BaseResponse

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by goldze on 2019/3/26.
 */
class DemoRepository private constructor(
    @param:NonNull private val mHttpDataSource: HttpDataSource,
    @param:NonNull private val mLocalDataSource: LocalDataSource
) : BaseModel(), HttpDataSource, LocalDataSource {

    val userName: String
        @Override
        get() = mLocalDataSource.getUserName()

    val password: String
        @Override
        get() = mLocalDataSource.getPassword()


    @Override
    fun simulationLogin(): Observable<Object> {
        return mHttpDataSource.simulationLogin()
    }

    @Override
    fun simulationLoadMore(): Observable<DemoEntity> {
        return mHttpDataSource.simulationLoadMore()
    }

    @Override
    fun demoGet(): Observable<BaseResponse<DemoEntity>> {
        return mHttpDataSource.demoGet()
    }

    @Override
    fun demoPost(catalog: String): Observable<BaseResponse<DemoEntity>> {
        return mHttpDataSource.demoPost(catalog)
    }

    @Override
    fun saveUserName(userName: String) {
        mLocalDataSource.saveUserName(userName)
    }

    @Override
    fun savePassword(password: String) {
        mLocalDataSource.savePassword(password)
    }

    companion object {
        @Volatile
        private var INSTANCE: DemoRepository? = null

        fun getInstance(
            httpDataSource: HttpDataSource,
            localDataSource: LocalDataSource
        ): DemoRepository? {
            if (INSTANCE == null) {
                synchronized(DemoRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DemoRepository(httpDataSource, localDataSource)
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
