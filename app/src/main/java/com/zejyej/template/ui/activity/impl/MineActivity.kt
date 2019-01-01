package com.zejyej.template.ui.activity.impl

import android.os.Bundle
import com.zejyej.template.R
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PMine
import com.zejyej.template.ui.activity.IMineView

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class MineActivity:BaseModuleMvpActivity<PMine>(),IMineView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_mine)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }
}