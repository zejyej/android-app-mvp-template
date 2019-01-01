package com.zejyej.base.util

import android.content.Context
import com.zejyej.base.common.BaseApplication


/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
object ContextUtil {

    fun getAppContext(): Context {
        return BaseApplication.getApplicationContext()
    }


}