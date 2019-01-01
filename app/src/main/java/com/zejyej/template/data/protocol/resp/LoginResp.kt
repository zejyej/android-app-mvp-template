package com.zejyej.template.data.protocol.resp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
data class LoginResp(
        val id:String?,
        val memAccount:String?,
        val nickname:String?,
        val memLevel:Int?,
        val photo:String?,
        val token:String?
)