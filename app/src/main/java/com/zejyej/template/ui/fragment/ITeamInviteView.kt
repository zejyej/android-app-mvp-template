package com.zejyej.template.ui.fragment

import com.zejyej.provider.ui.IBaseRefreshView
import com.zejyej.template.data.protocol.resp.TeamInviteResp
import com.zejyej.template.data.protocol.resp.TeamInviteUpgradeSingleResp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
interface ITeamInviteView: IBaseRefreshView {
    fun onCheckRemoteDataFirst(t: TeamInviteResp)
    fun onCheckRemoteDataSecond(t: List<TeamInviteUpgradeSingleResp>?)

}