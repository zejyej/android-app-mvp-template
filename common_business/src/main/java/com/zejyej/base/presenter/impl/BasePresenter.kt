package com.zejyej.base.presenter.impl

import android.content.Context
import com.zejyej.base.R

import com.zejyej.base.common.ResultStatus
import com.zejyej.base.presenter.IBasePresenter
import com.zejyej.base.ui.IBaseView
import com.zejyej.template.libselflibrary.net.NetWorkUtils
import com.trello.rxlifecycle2.LifecycleProvider

import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/4/28
 */
open class BasePresenter<T: IBaseView>:IBasePresenter {

    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    //检查网络
    override fun checkCanNetWork():Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        mView.handleError(ResultStatus.CODE_NETWORK_UNAVAILABLE,context.getString(R.string.network_unavailable))
        return false
    }

}