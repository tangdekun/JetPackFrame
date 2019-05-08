package com.djt.study.base.data

import android.content.Context
import com.djt.study.base.data.local.db.DbHelper
import com.djt.study.base.data.local.sharedpreference.PreferencesHelper
import com.djt.study.base.data.remote.ApiHelper
import com.google.gson.Gson
import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:31
 * @Email tangdekun0924@gmail.com
 */
class AppDataManager @Inject constructor(
    context: Context,
    dbHelper: DbHelper,
    preferencesHelper: PreferencesHelper,
    apiHelper: ApiHelper,
    gson: Gson
) :
    DataManager {
    /**
     * 参考demo  创建第一个函数时删除
     */


//    override fun insertAppInfo(appInfoEntity: AppInfoEntity): Observable<Boolean> {
//        return mDbHelper.insertAppInfo(appInfoEntity)
//    }


    private var mApiHelper: ApiHelper = apiHelper

    private var mDbHelper: DbHelper = dbHelper

    private var mPreferencesHelper: PreferencesHelper = preferencesHelper

    private var mGson: Gson? = gson

    private var mContext: Context? = context

}