package com.zejyej.base.rx

import com.zejyej.base.ui.IBaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
open class BaseObserver<T>(
        val baseView: IBaseView
): Observer<T> {

    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onNext(t: T) {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        if (e is BaseException) {
            baseView.handleError(e.code,e.msg)
        }else {
            baseView.handleError(0,"网络连接错误，请重试")
        }
    }



}