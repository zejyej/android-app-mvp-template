package com.zejyej.template.ui.activity

import com.zejyej.provider.ui.IBaseRefreshView
import com.zejyej.template.data.protocol.resp.MyTeamResp
import com.zejyej.template.data.protocol.resp.MyTeamUpgradeResp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IMyTeamView:IBaseRefreshView {
    fun onCheckRemoteDataFirst(t: MyTeamResp)
    fun onCheckRemoteDataSecond(t: MyTeamUpgradeResp)
}