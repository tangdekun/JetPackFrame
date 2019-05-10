package com.tdk.daggerdemo

import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @Author tangdekun
 * @Date 2018/7/30-10:45
 * @Email tangdekun0924@gmail.com
 */
@Module
class MainModule {


    //    @Named("tangdekun")
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