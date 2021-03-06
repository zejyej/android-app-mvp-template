package com.zejyej.template.ui.activity.impl

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.zejyej.base.extension.onClick
import com.zejyej.base.util.ToastUtil
import com.zejyej.template.R
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PResetPassword
import com.zejyej.template.ui.activity.IResetPasswordView
import com.zejyej.template.util.PatternUtils
import kotlinx.android.synthetic.main.app_activity_reset_password.*

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class ResetPasswordActivity:BaseModuleMvpActivity<PResetPassword>(),IResetPasswordView, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_reset_password)
        ivBack.onClick(this)
        flGetVerifyCodeWithSMS.onClick(this)
        tvVoice.onClick(this)
        btnSubmit.onClick(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.flGetVerifyCodeWithSMS -> {
                val mobile = etUsername.text.toString().trim()
                if(TextUtils.isEmpty(mobile)) {
                    ToastUtil.longToast(this, "手机号不能为空")
                    return
                }
                if(!PatternUtils.verifyDecimals(PatternUtils.MOBILE_PHONE, mobile)) {
                    ToastUtil.longToast(this,"请输入正确的手机号")
                    return
                }
                flGetVerifyCodeWithSMS.isEnabled = false
                object : Thread() {
                    override fun run() {
                        for (i in 59 downTo 0) {
                            try {
                                Thread.sleep(1000)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            runOnUiThread {
                                if (i <= 0) {
                                    tvGetVerifyCodeWithSMS.text = "获取验证码"
                                    flGetVerifyCodeWithSMS.isEnabled = true
                                } else {
                                    tvGetVerifyCodeWithSMS.text = i.toString().plus("秒后重新发送")
                                }
                            }
                        }
                    }
                }.start()
                mPresenter.getVerifyCode(VerifyCodeReq(
                        mobile,
                        "resetPassword",
                        "TEXT_SMS"
                ))

            }

            R.id.tvVoice -> {
                val mobile = etUsername.text.toString().trim()
                if(TextUtils.isEmpty(mobile)) {
                    ToastUtil.longToast(this, "手机号不能为空")
                    return
                }
                if(!PatternUtils.verifyDecimals(PatternUtils.MOBILE_PHONE, mobile)) {
                    ToastUtil.longToast(this,"请输入正确的手机号")
                    return
                }
                mPresenter.getVerifyCode(
                        VerifyCodeReq(
                                mobile,
                                "resetPassword",
                                "VOICE_SMS"
                        )
                )
            }

            R.id.btnSubmit -> {
                val mobile = etUsername.text.toString().trim()
                if(TextUtils.isEmpty(mobile)) {
                    ToastUtil.longToast(this, "手机号不能为空")
                    return
                }
                if(!PatternUtils.verifyDecimals(PatternUtils.MOBILE_PHONE, mobile)) {
                    ToastUtil.longToast(this,"请输入正确的手机号")
                    return
                }
                val password = etPwd.text.toString().trim()
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.longToast(this, "密码不能为空")
                    return
                }
                if (!PatternUtils.verifyDecimals(PatternUtils.PASSWORD, password)) {
                    ToastUtil.longToast(this, "密码格式不对，请输入6-16位字母和数字")
                    return
                }
                val verifyCode = etVerifyCode.text.toString().trim()
                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtil.longToast(this, "验证码不能为空")
                    return
                }
                ToastUtil.longToast(this, "开始重置密码")
                mPresenter.checkResetPassword(ResetPasswordReq(
                        mobile,
                        password,
                        verifyCode
                ))
            }
        }
    }


    override fun onCheckVerifyCode(t: Boolean) {
        if (t) {
            ToastUtil.longToast(this,"发送成功，请注意接收或接听")
        }
    }

    override fun onCheckResetPassword(t: Boolean) {
        if (t) {
            ToastUtil.longToast(this,"密码重置成功，请重新登陆")
            finish()
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