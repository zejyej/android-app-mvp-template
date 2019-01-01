package com.zejyej.template.ui.activity

import com.zejyej.base.ui.IBaseView

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IResetPasswordView:IBaseView {
    fun onCheckVerifyCode(t: Boolean)
    fun onCheckResetPassword(t: Boolean)
}