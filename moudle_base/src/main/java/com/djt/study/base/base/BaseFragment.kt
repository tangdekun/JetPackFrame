package com.djt.study.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment : Fragment() {


    open lateinit var model: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(getViewModel())
        } ?: throw Exception("Invalid Activity")
    }

    abstract fun getLayoutId(): Int


    /**
     * 绑定ViewModel
     */
    abstract fun <T> getViewModel(): Class<T>

}