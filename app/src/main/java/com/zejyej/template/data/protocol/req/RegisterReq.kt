package com.zejyej.template.data.protocol.req

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
data class RegisterReq (
        val memAccount:String,
        val memPassword:String,
        val sourceChannel:String,
        val verifyCode:String
)