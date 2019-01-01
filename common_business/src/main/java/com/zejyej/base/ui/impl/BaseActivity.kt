package com.zejyej.base.ui.impl

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.zejyej.base.common.impl.AppManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

import org.jetbrains.anko.find

/**
 * @desc 业务无关基类
 * @author zejyej
 * @date 2018/4/27
 */
open class BaseActivity: RxAppCompatActivity() {

    val contentView:View
    get() {
        return find<FrameLayout>(android.R.id.content).getChildAt(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    override fun onDestroy() {
        AppManager.appManagerInstance.removeActivity(this)
        super.onDestroy()
    }


}