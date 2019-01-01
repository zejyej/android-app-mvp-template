package com.zejyej.template.presenter

import com.zejyej.template.data.protocol.req.LoginReq

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
interface IPLogin {
    //登陆
    fun login(req:LoginReq)
}