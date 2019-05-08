package com.djt.base.utils

object ResourceUtilsExtends {

    fun getString(id: Int): String {
        return Utils.getContext().getString(id)
    }


    fun getStringArray(id: Int): Array<out String>? {
        return Utils.getContext().resources.getStringArray(id)
    }

    fun getIntArray(id: Int): IntArray? {
        return Utils.getContext().resources.getIntArray(id)
    }


    fun getDrawableArray(id: Int): IntArray {
        val typedArray = Utils.getContext().resources.obtainTypedArray(id)
        val array = IntArray(typedArray.length())
        for (i in 0 until typedArray.length()) {
            array[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()
        return array
    }
}