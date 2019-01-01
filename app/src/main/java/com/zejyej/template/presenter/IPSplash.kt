package com.zejyej.template.presenter

import com.zejyej.template.data.protocol.req.LoginReq

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
interface IPSplash {

    fun checkLogin(req: LoginReq)
}