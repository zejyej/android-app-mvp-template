package com.zejyej.base.injection.component

import android.content.Context
import com.zejyej.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * @desc
 * @author zejyej
 * @date 2018/4/28
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun context():Context

}