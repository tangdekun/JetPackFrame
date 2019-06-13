package com.tdk.daggerdemo.di.key
import androidx.appcompat.app.AppCompatActivity
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @Author tangdekun
 * @Date 2019/5/9-15:24
 * @Email tangdekun0924@gmail.com
 */
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ActivityKey(val value: KClass<out AppCompatActivity>)