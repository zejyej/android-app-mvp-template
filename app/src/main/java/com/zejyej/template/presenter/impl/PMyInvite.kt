package com.zejyej.template.presenter.impl

import com.zejyej.base.bus.RxBus
import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.resp.MyInviteResp
import com.zejyej.template.event.MyInviteNumBean
import com.zejyej.template.event.TeamInviteNumBean
import com.zejyej.template.presenter.IPMyInvite
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.fragment.IMyInviteView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class PMyInvite @Inject constructor():BasePresenter<IMyInviteView>(),IPMyInvite {

    fun getRemoteData(memId: String, page: Int, rows: Int, type: String) {
        userService.myInvite(
                memId,page,rows,type
        ).excute(object :BaseObserver<MyInviteResp>(mView){
            override fun onNext(t: MyInviteResp) {
                RxBus.get().post(MyInviteNumBean(t.inviteCount?:0))
                RxBus.get().post(TeamInviteNumBean(t.teamInviteCount?:0))
                mView.handleRemoteData(t.myInviteList)
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