package com.zejyej.base.injection.module


import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Module
import dagger.Provides

/**
 * @desc
 * @author zejyej
 * @date 2018/4/28
 */
@Module
class LifecycleProviderModule(
      private val lifecyleProvider: LifecycleProvider<*>
) {

    @Provides
    fun provideLifecycleProvider(): LifecycleProvider<*> {
        return lifecyleProvider
    }

}