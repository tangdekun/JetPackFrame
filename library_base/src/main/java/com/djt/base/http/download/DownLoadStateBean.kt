package com.djt.base.http.download

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

/**
 * Created by goldze on 2017/5/11.
 */

class DownLoadStateBean constructor(
    var total: Long,   //  文件总大小
    var bytesLoaded: Long,  //已加载文件的大小
    var tag: String?   // 多任务下载时的一个标记
) : Parcelable {


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(total)
        parcel.writeLong(bytesLoaded)
        parcel.writeString(tag)
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()
    )


    companion object CREATOR : Parcelable.Creator<DownLoadStateBean> {
        override fun createFromParcel(source: Parcel): DownLoadStateBean {
            return DownLoadStateBean(source)
        }

        override fun newArray(size: Int): Array<DownLoadStateBean?> {
            return arrayOfNulls(size)
        }
    }
}