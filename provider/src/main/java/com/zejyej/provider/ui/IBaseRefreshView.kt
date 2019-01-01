package com.zejyej.provider.ui

import com.zejyej.base.ui.IBaseView

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
interface IBaseRefreshView:IBaseView {
    fun enableRefreshLayout()
    fun disableRefreshLayout()
    fun finishRefreshLayout()
}