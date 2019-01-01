package com.zejyej.template.presenter.impl

import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.template.presenter.IPTodayVisitor
import com.zejyej.template.ui.activity.ITodayVisitorView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PTodayVisitor @Inject constructor(): BasePresenter<ITodayVisitorView>(),IPTodayVisitor{

}