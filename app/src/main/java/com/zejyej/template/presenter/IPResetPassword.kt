package com.zejyej.template.presenter

import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IPResetPassword {
    fun checkResetPassword(req: ResetPasswordReq)
    fun getVerifyCode(verifyCodeReq: VerifyCodeReq)
}