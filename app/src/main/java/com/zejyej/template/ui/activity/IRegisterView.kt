package com.zejyej.template.ui.activity

import com.zejyej.base.ui.IBaseView

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IRegisterView: IBaseView {
    fun onCheckVerifyCode(t: Boolean)
    fun onCheckRegister(b: Boolean)
}