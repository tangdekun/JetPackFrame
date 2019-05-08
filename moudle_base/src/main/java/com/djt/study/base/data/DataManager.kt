package com.djt.study.base.data

import com.djt.study.base.data.local.db.DbHelper
import com.djt.study.base.data.local.sharedpreference.PreferencesHelper
import com.djt.study.base.data.remote.ApiHelper


/**
 * @Author tangdekunZ
 * @Date 2018/7/30-14:30
 * @Email tangdekun0924@gmail.com
 */
interface DataManager : ApiHelper, DbHelper, PreferencesHelper