package com.zejyej.template.data.protocol.req

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
data class VerifyCodeReq(
        val memAccount:String?,
        //register  OR   resetPassword
        val verifyScene:String?,
        //TEXT_SMS OR VOICE_SMS
        val verifyType:String?
)