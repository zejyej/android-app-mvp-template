package com.zejyej.template.presenter

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IPMyTeam {

    fun getRemoteDataFirst(memId: String, pageUp: Int, rows: Int)
    fun getRemoteDataSecond(memId: String, pageBelow: Int, rows: Int)
}