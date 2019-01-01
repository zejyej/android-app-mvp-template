package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.resp.TeamInviteResp
import com.zejyej.template.data.protocol.resp.TeamInviteUpgradeSingleResp
import com.zejyej.template.presenter.IPTeamInvite
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.fragment.ITeamInviteView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class PTeamInvite @Inject constructor():BasePresenter<ITeamInviteView>(),IPTeamInvite {
    override fun getRemoteDataSecond(memId: String, pageBlow: Int, rows: Int) {
        userService.teamInviteUpgrade(memId,pageBlow,rows)
                .excute(object : BaseObserver<List<TeamInviteUpgradeSingleResp>?>(mView) {
                    override fun onNext(t: List<TeamInviteUpgradeSingleResp>?) {
                        mView.onCheckRemoteDataSecond(t)
                    }
                },lifecycleProvider)
    }

    override fun getRemoteDataFirst(memId: String, pageUp: Int, rows: Int,type:String) {
        userService.teamInvite(memId,pageUp,rows,type)
                .excute(object : BaseObserver<TeamInviteResp>(mView) {

                    override fun onNext(t: TeamInviteResp) {
                        mView.onCheckRemoteDataFirst(t)
                    }

                },lifecycleProvider)
    }




    @Inject
    lateinit var userService:UserService





}