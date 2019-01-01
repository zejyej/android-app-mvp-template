package com.zejyej.base.ui.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zejyej.base.common.BaseApplication
import com.zejyej.base.injection.component.ActivityComponent
import com.zejyej.base.injection.component.DaggerActivityComponent
import com.zejyej.base.injection.module.ActivityModule
import com.zejyej.base.injection.module.LifecycleProviderModule
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.ui.IBaseView
import com.zejyej.base.util.ToastUtil
import com.zejyej.base.widgets.dialog.CommonProgressDialog
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
abstract class BaseMvpFragment<T: BasePresenter<*>>:BaseFragment(),IBaseView{
    @Inject
    lateinit var mPresenter: T


    var mActivityComponent: ActivityComponent ?= null

    var mCommonProgressDialog: CommonProgressDialog ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initActivityInjection()
        injectComponent()

        context?.let { it-> mCommonProgressDialog = CommonProgressDialog.create(it)}
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected abstract fun injectComponent()


    override fun showLoadingPage() {

    }

    override fun showContentPage() {

    }

    override fun showEmptyPage() {

    }

    override fun showErrorPage() {

    }


    private fun initActivityInjection() {
        activity?.let {
            mActivityComponent = DaggerActivityComponent.builder()
                    .appComponent((it.application as BaseApplication).appComponent)
                    .activityModule(ActivityModule(it))
                    .lifecycleProviderModule(LifecycleProviderModule(this))
                    .build()
        }


    }


    override fun handleError(status: Int, msg: String) {
        showErrorMsg(msg)
    }

    private fun showErrorMsg(msg: String) {
        ToastUtil.longToast(BaseApplication.getApplicationContext(),msg)
    }


    override fun showLoading() {
        mCommonProgressDialog?.showLoading()
    }

    override fun hideLoading() {
        mCommonProgressDialog?.hideLoading()
    }
}