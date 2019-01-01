package com.zejyej.template.presenter.impl

import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.template.presenter.IPAddReceiveInfo
import com.zejyej.template.ui.activity.IAddReceiveInfoView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/7
 */
class PAddReceiveInfo @Inject constructor(): BasePresenter<IAddReceiveInfoView>(),IPAddReceiveInfo {
}