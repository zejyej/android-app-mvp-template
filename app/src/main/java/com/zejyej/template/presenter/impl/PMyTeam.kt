package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.resp.MyTeamResp
import com.zejyej.template.data.protocol.resp.MyTeamUpgradeResp
import com.zejyej.template.presenter.IPMyTeam
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.activity.IMyTeamView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PMyTeam @Inject constructor():BasePresenter<IMyTeamView>(),IPMyTeam {
    override fun getRemoteDataSecond(memId: String, pageBelow: Int, rows: Int) {
        userService.myTeamUpgrade(memId,pageBelow,rows)
                .excute(object :BaseObserver<MyTeamUpgradeResp>(mView){

                    override fun onNext(t: MyTeamUpgradeResp) {
                        mView.onCheckRemoteDataSecond(t)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        mView.finishRefreshLayout()
                        mView.enableRefreshLayout()
                    }

                },lifecycleProvider)
    }

    override fun getRemoteDataFirst(memId: String, pageUp: Int, rows: Int) {
        userService.myTeam(memId,pageUp,rows)
                .excute(object :BaseObserver<MyTeamResp>(mView){

                    override fun onNext(t: MyTeamResp) {
                        mView.onCheckRemoteDataFirst(t)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        mView.finishRefreshLayout()
                        mView.enableRefreshLayout()
                    }

                },lifecycleProvider)
    }

    @Inject
    lateinit var userService: UserService
}