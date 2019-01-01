package com.zejyej.base.injection.module

import android.app.Activity
import com.zejyej.base.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
@Module
class ActivityModule(private val activity: Activity) {
    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return activity
    }
}