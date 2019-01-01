package com.zejyej.template.data.protocol.req

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
data class ResetPasswordReq(
        val memAccount:String,
        val memPassword:String,
        val verifyCode:String
)