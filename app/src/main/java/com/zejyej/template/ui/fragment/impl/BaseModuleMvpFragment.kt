package com.zejyej.template.ui.fragment.impl

import android.os.Bundle
import android.view.View
import com.zejyej.base.common.ResultStatus
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.ui.impl.BaseMvpFragment
import com.zejyej.template.ui.activity.impl.LoginActivity
import org.jetbrains.anko.support.v4.intentFor

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
abstract class BaseModuleMvpFragment<T: BasePresenter<*>>: BaseMvpFragment<T>() {

    override fun handleError(status: Int, msg: String) {
        super.handleError(status, msg)
        when(status) {
            ResultStatus.CODE_SUCCESS -> {
                hideLoading()
            }
            ResultStatus.CODE_LOGIN -> {
                gotoLogin()
            }
            ResultStatus.CODE_VIP -> {
                gotoVip()
            }
            else -> {
                showErrorPage()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    abstract fun initView()
    abstract fun loadData()



    private fun gotoVip() {
        startActivity(intentFor<LoginActivity>())
    }

    private fun gotoLogin() {
        startActivity(intentFor<LoginActivity>())
    }

}