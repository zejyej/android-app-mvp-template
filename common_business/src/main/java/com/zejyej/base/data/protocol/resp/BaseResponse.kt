package com.zejyej.base.data.protocol.resp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
data class BaseResponse<out T>(
        val code:Int?,
        val message: String?,
        val data: T?
)