package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.resp.MyFansResp
import com.zejyej.template.presenter.IPMyFans
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.fragment.IMyFansView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PMyFans @Inject constructor():BasePresenter<IMyFansView>(),IPMyFans {
    override fun getRemoteData(memId: String, page: Int, rows: Int, type: String) {
        userService.myFans(memId,page,rows,type)
                .excute(object :BaseObserver<MyFansResp>(mView){
                    override fun onNext(t: MyFansResp) {
                        super.onNext(t)
                        mView.onCheckRemoteData(t)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        mView.finishRefreshLayout()
                        mView.enableLoadMore()
                        mView.enableRefreshLayout()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView.finishRefreshLayout()
                        mView.enableLoadMore()
                        mView.enableRefreshLayout()
                        mView.showErrorPage()
                    }

                },lifecycleProvider)
    }

    @Inject
    lateinit var userService: UserService
}