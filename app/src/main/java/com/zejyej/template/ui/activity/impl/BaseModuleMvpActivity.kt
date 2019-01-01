package com.zejyej.template.ui.activity.impl

import com.zejyej.base.common.ResultStatus
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.ui.impl.BaseMvpActivity
import org.jetbrains.anko.intentFor

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
abstract class BaseModuleMvpActivity<T: BasePresenter<*>>:BaseMvpActivity<T>() {



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

    override fun showLoadingPage() {

    }

    override fun showContentPage() {

    }

    override fun showEmptyPage() {

    }

    override fun showErrorPage() {

    }


    private fun gotoVip() {
        startActivity(intentFor<LoginActivity>())
    }

    private fun gotoLogin() {
        startActivity(intentFor<LoginActivity>())
    }

}