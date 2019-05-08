package com.djt.study.demo.ui.network.detail

import android.app.Application
import android.databinding.ObservableField
import com.djt.study.demo.entity.DemoEntity
import com.djt.mvvm.base.BaseViewModel

/**
 * Created by goldze on 2017/7/17.
 */

class DetailViewModel(@NonNull application: Application) : BaseViewModel(application) {
    var entity: ObservableField<DemoEntity.ItemsEntity>? = ObservableField()

    fun setDemoEntity(entity: DemoEntity.ItemsEntity) {
        this.entity!!.set(entity)
    }

    @Override
    fun onDestroy() {
        super.onDestroy()
        entity = null
    }
}
