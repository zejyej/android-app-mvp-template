package com.zejyej.template.ui.fragment

import com.zejyej.provider.ui.IBaseRefreshAndLoadMoreView
import com.zejyej.template.data.protocol.resp.MyFansResp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IMyFansView: IBaseRefreshAndLoadMoreView {
    fun onCheckRemoteData(t: MyFansResp)
    override fun showErrorPage()
}