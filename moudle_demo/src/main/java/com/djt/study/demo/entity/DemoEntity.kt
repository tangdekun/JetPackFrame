package com.djt.study.demo.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by goldze on 2017/7/17.
 */

class DemoEntity {
    var nextPageToken: String? = null
    var prevPageToken: String? = null
    var requestCount: Int = 0
    var responseCount: Int = 0
    var totalResults: Int = 0
    var items: List<ItemsEntity>? = null

    class ItemsEntity : Parcelable {
        var detail: String? = null
        var href: String? = null
        var id: Int = 0
        var img: String? = null
        var name: String? = null
        var pubDate: String? = null
        var type: Int = 0

        @Override
        fun describeContents(): Int {
            return 0
        }

        @Override
        fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(this.detail)
            dest.writeString(this.href)
            dest.writeInt(this.id)
            dest.writeString(this.img)
            dest.writeString(this.name)
            dest.writeString(this.pubDate)
            dest.writeInt(this.type)
        }

        constructor() {}

        protected constructor(`in`: Parcel) {
            this.detail = `in`.readString()
            this.href = `in`.readString()
            this.id = `in`.readInt()
            this.img = `in`.readString()
            this.name = `in`.readString()
            this.pubDate = `in`.readString()
            this.type = `in`.readInt()
        }

        companion object {

            val CREATOR: Creator<ItemsEntity> = object : Creator<ItemsEntity>() {
                @Override
                fun createFromParcel(source: Parcel): ItemsEntity {
                    return ItemsEntity(source)
                }

                @Override
                fun newArray(size: Int): Array<ItemsEntity> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
