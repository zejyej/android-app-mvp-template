package com.zejyej.template.presenter

import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IPRegister {
    fun getVerifyCode(verifyCodeReq: VerifyCodeReq)
    fun register(registerReq: RegisterReq)
}