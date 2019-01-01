package com.zejyej.base.ui

/**
 * @desc
 * @author zejyej
 * @date 2018/4/27
 */
interface IBaseView {
    //异常处理
    fun handleError(status:Int,msg:String)
    //加载提示
    fun showLoading()
    //隐藏提示
    fun hideLoading()
    //显示加载页
    fun showLoadingPage()
    //显示内容页
    fun showContentPage()
    //显示空页
    fun showEmptyPage()
    //显示错误页
    fun showErrorPage()

}