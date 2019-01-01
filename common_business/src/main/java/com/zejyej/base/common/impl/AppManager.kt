package com.zejyej.base.common.impl

import android.app.Activity
import com.zejyej.base.common.IAppManager
import java.util.*

/**
 * @desc App管理类
 * @author zejyej
 * @date 2018/4/28
 */
class AppManager private constructor(): IAppManager {


    private val activityStack:Stack<Activity> = Stack()

    companion object {
        val appManagerInstance: AppManager by lazy { AppManager() }
    }

    override fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    override fun removeActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    override fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    override fun removeAllActivity() {
        activityStack.forEach {
            activity -> activity.finish()
        }
        activityStack.clear()
    }

}

