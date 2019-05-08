package com.djt.study.base.data.remote

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:29
 * @Email tangdekun0924@gmail.com
 */
@Singleton
class AppApiHelper @Inject constructor(val apis: Apis) : ApiHelper {

    /**
     * 参考demo  创建第一个函数时删除
     */

//
//    override fun gameRecommend(userId: Int, grade: Int, machineName: String): Observable<RecommendGame> {
//        return apis.gameRecommend(userId, grade, machineName)
//    }


}