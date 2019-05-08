/**
 * 参考
 */

























package com.djt.study.base.data.local.db.entity

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @Author tangdekun
 * @Date 2018/8/2-13:57
 * @Email tangdekun0924@gmail.com
 * 技能实体类
 * @Ignore 是用于多个构造函数的时候,@Ignore注解告诉Room哪个用，哪个不用。
 */
@Entity(tableName = "appinfo")
class AppInfoEntity @Ignore constructor() {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo()
    var appName: String? = ""

    @ColumnInfo()
    var packageName: String? = ""
    @ColumnInfo()
    var isBeingControled: Boolean? = false

    @Ignore
    var drawable: Drawable? = null

    constructor(appName: String?, packageName: String?, isBeingControled: Boolean) : this() {
        this.appName = appName
        this.packageName = packageName
        this.isBeingControled = isBeingControled
    }

    @Ignore
    constructor(appName: String?, packageName: String?, drawable: Drawable?) : this(appName, packageName, false) {
        this.drawable = drawable
    }
}