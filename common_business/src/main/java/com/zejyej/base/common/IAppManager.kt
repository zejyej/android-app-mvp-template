package com.zejyej.base.common

import android.app.Activity

/**
 * @desc
 * @author zejyej
 * @date 2018/4/28
 */
interface IAppManager {

    fun addActivity(activity: Activity)

    fun removeActivity(activity: Activity)

    fun currentActivity():Activity

    fun removeAllActivity()

}