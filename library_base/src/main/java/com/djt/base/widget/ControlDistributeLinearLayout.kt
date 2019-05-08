package com.djt.base.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

import com.djt.base.R

/**
 * Created by goldze on 2017/3/16.
 * 控制事件分发的LinearLayout
 */
class ControlDistributeLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    //默认是不拦截事件,分发事件给子View
    var isDistributeEvent = false

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ControlDistributeLinearLayout)
        isDistributeEvent = typedArray.getBoolean(R.styleable.ControlDistributeLinearLayout_distribute_event, false)
    }

    /**
     * 重写事件分发方法,false 为分发 , true 为父控件自己消耗, 由外面传进来的参数决定
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isDistributeEvent
    }
}
