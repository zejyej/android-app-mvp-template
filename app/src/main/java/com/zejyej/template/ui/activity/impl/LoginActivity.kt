package com.zejyej.template.ui.activity.impl

import android.os.Bundle
import android.view.View
import com.zejyej.base.extension.onClick
import com.zejyej.base.util.ToastUtil
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PLogin
import com.zejyej.template.ui.activity.ILoginView
import kotlinx.android.synthetic.main.app_activity_login.*
import org.jetbrains.anko.intentFor


/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class LoginActivity:BaseModuleMvpActivity<PLogin>(),ILoginView, View.OnClickListener {

    override fun intentForMineActivity() {
        startActivity(intentFor<TodayVisitorActivity>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_login)
        initView()
    }

    private fun initView() {
        tvRegister.onClick(this)
        tvResetPassword.onClick(this)
        btnLogin.onClick(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.tvRegister -> {
                startActivity(intentFor<RegisterActivity>())
            }

            R.id.tvResetPassword -> {
                startActivity(intentFor<ResetPasswordActivity>())
            }

            R.id.btnLogin -> {
                if (etUsername.text.isNullOrEmpty().not() && etPwd.text.isNullOrEmpty().not()) {
                    mPresenter.login(
                            LoginReq(
                                    etUsername.text.trim().toString(),
                                    etPwd.text.trim().toString()
                            )
                    )
                }else {
                    ToastUtil.shortToast(MyApplication.getApplicationContext(),"账号或密码不能为空")
                }
            }
        }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }
}