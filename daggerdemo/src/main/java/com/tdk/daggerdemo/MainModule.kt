package com.tdk.daggerdemo

import com.tdk.daggerdemo.second.SecondActivity
import com.tdk.daggerdemo.second.ui.second.SecondFragment
import com.tdk.daggerdemo.second.ui.second.SecondFragmentModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Named

/**
 * @Author tangdekun
 * @Date 2018/7/30-10:45
 * @Email tangdekun0924@gmail.com
 */
@Module
class MainActivityProvideModule {


    @Provides
    fun provideStudent(): Student {
        return Student("tangdekun", 26, provideLesson())
    }


    @Provides
    fun provideLesson(): Lesson {
        return Lesson("语文", 98)
    }


    @Provides
    @Named("")
    fun provideLessonMath(): Lesson {
        return Lesson("数学", 98)
    }


    @Named("tdk")
    @Provides
    fun providerStudent(): Student {
        return Student("tdk", 26)
    }


}

@Module(
    subcomponents = [MainComponent::class, SecondActivityComponent::class, SecondFragmentComponent::class],
    includes = [MainActivityProvideModule::class, SecondFragmentModule::class]
)
abstract class MainActivityBindModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindYourActivityInjectorFactory(factory: MainComponent.Builder): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(SecondActivity::class)
    abstract fun bindSecondActivityInjectorFactory(factory: SecondActivityComponent.Builder): AndroidInjector.Factory<*>


    @Binds
    @IntoMap
    @ClassKey(SecondFragment::class)
    abstract fun bindSecondFragmentInjectorFactory(factory: SecondFragmentComponent.Builder): AndroidInjector.Factory<*>


}
