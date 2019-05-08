package com.djt.study.base.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djt.study.base.data.local.db.dao.AppInfoDao
import com.djt.study.base.data.local.db.entity.AppInfoEntity


/**
 * @Author tangdekun
 * @Date 2018/8/2-11:57
 * @Email tangdekun0924@gmail.com
 */
@Database(entities = [AppInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {


    /**
     * demo数据
     */
    abstract fun appInfoDao(): AppInfoDao

}