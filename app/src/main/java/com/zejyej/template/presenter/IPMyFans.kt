package com.zejyej.template.presenter

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IPMyFans {
    fun getRemoteData(memId: String, page: Int, rows: Int, type: String)
}