package com.zejyej.template.common

import android.content.Context
import com.zejyej.base.common.BaseApplication

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
class MyApplication:BaseApplication() {

    companion object {
        private lateinit var appContext: Context
        fun getApplicationContext(): Context {
            return appContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

    }

}