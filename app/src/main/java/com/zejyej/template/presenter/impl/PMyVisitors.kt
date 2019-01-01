package com.zejyej.template.presenter.impl

import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.template.data.protocol.resp.MyVisitorsResp
import com.zejyej.template.presenter.IPMyVisitors
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.fragment.IMyVisitorsView
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class PMyVisitors @Inject constructor():BasePresenter<IMyVisitorsView>(),IPMyVisitors {

    @Inject
    lateinit var userService: UserService

    override fun getRemoteData(memId: String, page: Int, rows: Int, type: String) {
        userService.myVisitors(memId,page,rows,type)
                .excute(object :BaseObserver<MyVisitorsResp>(mView) {

                    override fun onNext(t: MyVisitorsResp) {
                        super.onNext(t)
                        mView.onCheckRemoteData(t)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        mView.finishRefreshLayout()
                        mView.enableRefreshLayout()
                        mView.enableLoadMore()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView.finishRefreshLayout()
                        mView.enableRefreshLayout()
                        mView.enableLoadMore()
                        mView.showErrorPage()
                    }

                },lifecycleProvider)
    }


}