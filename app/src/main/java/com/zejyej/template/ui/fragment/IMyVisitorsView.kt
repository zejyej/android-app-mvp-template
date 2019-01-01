package com.zejyej.template.ui.fragment

import com.zejyej.provider.ui.IBaseRefreshAndLoadMoreView
import com.zejyej.template.data.protocol.resp.MyVisitorsResp

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
interface IMyVisitorsView:IBaseRefreshAndLoadMoreView {
    fun onCheckRemoteData(t: MyVisitorsResp)
}