package com.zejyej.base.ui.impl

import android.annotation.SuppressLint
import android.os.Bundle
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
 * @desc 业务相关MVP类
 * @author zejyej
 * @date 2018/4/27
 */
@SuppressLint("Registered")
abstract class BaseMvpActivity<T: BasePresenter<*>>: BaseActivity(),IBaseView {

    @Inject
    lateinit var mPresenter: T


    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mCommonProgressDialog: CommonProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
        mCommonProgressDialog = CommonProgressDialog.create(this)
    }

    protected abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun handleError(status: Int, msg: String) {
       showErrorMsg(msg)
    }

    private fun showErrorMsg(msg: String) {
        ToastUtil.longToast(BaseApplication.getApplicationContext(),msg)
    }



    override fun showLoading() {
        mCommonProgressDialog.showLoading()
    }

    override fun hideLoading() {
        mCommonProgressDialog.hideLoading()
    }

}