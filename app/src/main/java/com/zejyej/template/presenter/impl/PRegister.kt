package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.data.protocol.resp.RegisterResp
import com.zejyej.template.presenter.IPRegister
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.activity.IRegisterView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PRegister @Inject constructor():BasePresenter<IRegisterView>(),IPRegister {
    override fun getVerifyCode(verifyCodeReq: VerifyCodeReq) {
        userService.verifyCode(verifyCodeReq)
                .excute(object : BaseObserver<Boolean> (mView) {
                    override fun onNext(t: Boolean) {
                        super.onNext(t)
                        mView.onCheckVerifyCode(t)
                    }
                },lifecycleProvider)

    }

    override fun register(registerReq: RegisterReq) {
        userService.register(registerReq)
                .excute(object : BaseObserver<RegisterResp>(mView) {
                    override fun onNext(t: RegisterResp) {
                        super.onNext(t)
                        mView.onCheckRegister(true)
                    }
                },lifecycleProvider)
    }

    @Inject
    lateinit var userService: UserService


}