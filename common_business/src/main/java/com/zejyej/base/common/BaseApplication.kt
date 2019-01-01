package com.zejyej.base.common

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.zejyej.base.injection.component.AppComponent
import com.zejyej.base.injection.component.DaggerAppComponent
import com.zejyej.base.injection.module.AppModule
import com.zejyej.base.widgets.image.GlideImageLoader
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.qiyukf.unicorn.api.SavePowerConfig
import com.qiyukf.unicorn.api.StatusBarNotificationConfig
import com.qiyukf.unicorn.api.Unicorn
import com.qiyukf.unicorn.api.YSFOptions

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    companion object {
        private lateinit var appContext: Context
        fun getApplicationContext(): Context {
            return appContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        injectApp()
        appContext = this
        //logger
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BaseConstant.isDebug
            }
        })
        //hawk
        Hawk.init(this).build()
        //qiyukf
        Unicorn.init(this, "#", options(), GlideImageLoader(this))

    }

    // 如果返回值为null，则全部使用默认参数。
    private fun options(): YSFOptions {
        val options = YSFOptions()
        options.statusBarNotificationConfig = StatusBarNotificationConfig()
        options.savePowerConfig = SavePowerConfig()
        return options
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //multidex
        MultiDex.install(this)

    }


    private fun injectApp() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}