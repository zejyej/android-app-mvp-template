package com.zejyej.base.injection.component

import android.app.Activity
import android.content.Context
import com.zejyej.base.injection.module.ActivityModule
import com.zejyej.base.injection.module.LifecycleProviderModule
import com.zejyej.base.injection.scope.ActivityScope
import com.trello.rxlifecycle2.LifecycleProvider

import dagger.Component

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
@ActivityScope
@Component(dependencies = [AppComponent::class],modules = [ActivityModule::class, LifecycleProviderModule::class])
interface ActivityComponent {

    fun context(): Context

    fun activity(): Activity

    fun lifecycleProvider(): LifecycleProvider<*>
}