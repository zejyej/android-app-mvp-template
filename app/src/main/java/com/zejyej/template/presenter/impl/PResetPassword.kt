package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.presenter.IPResetPassword
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.activity.IResetPasswordView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PResetPassword @Inject constructor():BasePresenter<IResetPasswordView>(),IPResetPassword {

    override fun checkResetPassword(req: ResetPasswordReq) {
        userService.resetPassword(req)
                .excute(object :BaseObserver<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        super.onNext(t)
                        mView.onCheckResetPassword(t)
                    }
                },lifecycleProvider)
    }

    override fun getVerifyCode(verifyCodeReq: VerifyCodeReq) {

        userService.verifyCode(verifyCodeReq)
                .excute(object :BaseObserver<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        super.onNext(t)
                        mView.onCheckVerifyCode(t)
                    }

                },lifecycleProvider)

    }

    @Inject
    lateinit var userService: UserService
}