package com.zejyej.template.ui.fragment

import com.zejyej.provider.ui.IBaseRefreshView
import com.zejyej.template.data.protocol.resp.MyInviteResp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
interface IMyInviteView:IBaseRefreshView{

    fun handleRemoteData(myInviteList: List<MyInviteResp.MyInvite>?)

}