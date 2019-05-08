/**
 * 参考
 */





















package com.djt.study.base.data.local.db.dao

import androidx.room.*
import com.djt.study.base.data.local.db.entity.AppInfoEntity

/**
 * @Author tangdekun
 * @Date 2018/8/2-15:00
 * @Email tangdekun0924@gmail.com
 */
@Dao
interface AppInfoDao {
    /**
     * 新增,遇到冲突采取替换策略
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appInfoEntity: AppInfoEntity): Long

    /**
     * 新增,遇到冲突采取替换策略
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(appInfoEntity: List<AppInfoEntity>)

    @Query("DELETE FROM appinfo WHERE packageName =(:packageName)")
    fun delete(packageName: String)

    @Update
    fun update(vararg appInfoEntity: AppInfoEntity): Int

    @Query("SELECT * FROM appinfo WHERE isBeingControled=(:isBeingControled)")
    fun queryAll(isBeingControled: Boolean): List<AppInfoEntity>

    @Query("SELECT * FROM appinfo WHERE packageName=(:pkg)")
    fun queryForPkg(pkg: String): List<AppInfoEntity>


}