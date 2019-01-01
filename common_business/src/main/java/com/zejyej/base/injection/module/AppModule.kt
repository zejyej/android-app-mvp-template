package com.zejyej.base.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @desc
 * @author zejyej
 * @date 2018/4/28
 */
@Module
class AppModule(private val context:Application) {

    @Singleton
    @Provides
    fun provideContext():Context {
        return context
    }

}