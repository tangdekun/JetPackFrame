package com.djt.study.demo.entity

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by goldze on 2017/7/17.
 */

class FormEntity : BaseObservable, Parcelable {
    var id: String? = null
    var name: String? = null
    var sex: String? = null
    var bir: String? = null
    var hobby: String? = null
    var marry: Boolean? = null

    constructor() {}

    @Override
    fun describeContents(): Int {
        return 0
    }

    @Override
    fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.id)
        dest.writeString(this.name)
        dest.writeString(this.sex)
        dest.writeString(this.bir)
        dest.writeString(this.hobby)
        dest.writeValue(this.marry)
    }

    protected constructor(`in`: Parcel) {
        this.id = `in`.readString()
        this.name = `in`.readString()
        this.sex = `in`.readString()
        this.bir = `in`.readString()
        this.hobby = `in`.readString()
        this.marry = `in`.readValue(Boolean::class.java!!.getClassLoader())
    }

    companion object {

        val CREATOR: Creator<FormEntity> = object : Creator<FormEntity>() {
            @Override
            fun createFromParcel(source: Parcel): FormEntity {
                return FormEntity(source)
            }

            @Override
            fun newArray(size: Int): Array<FormEntity> {
                return arrayOfNulls(size)
            }
        }
    }
}
