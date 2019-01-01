package com.zejyej.template.presenter

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
interface IPTeamInvite {
    fun getRemoteDataFirst(memId: String, pageUp: Int, rows: Int,type:String)
    fun getRemoteDataSecond(memId: String, pageBlow: Int, rows: Int)

}