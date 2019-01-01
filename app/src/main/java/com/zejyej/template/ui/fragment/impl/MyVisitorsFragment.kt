package com.zejyej.template.ui.fragment.impl

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zejyej.base.bus.RxBus
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.extension.startLoading
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.protocol.resp.MyVisitorsResp
import com.zejyej.template.event.TodayVisitorNumberBean
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PMyVisitors
import com.zejyej.template.ui.fragment.IMyVisitorsView
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.header.MaterialHeader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.app_fragment_my_visitors.*

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class MyVisitorsFragment:BaseModuleMvpFragment<PMyVisitors>(), IMyVisitorsView, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var memId: String
    private var page: Int = BaseConstant.ONE
    private val rows: Int = BaseConstant.TEN
    private val type = BaseConstant.visitor
    private var adapter: EnterShopAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.app_fragment_my_visitors,container,false)
    }

    override fun initView() {
        configRefreshLayout()
    }

    private fun configRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            refreshLayout.isEnableRefresh = false
            disableLoadMore()
            recyclerView?.scrollToPosition(0)
            page = BaseConstant.ONE
            mPresenter.getRemoteData(memId,page,rows,type)

        }
        //set RefreshLayout
        refreshLayout.refreshHeader = MaterialHeader(MyApplication.getApplicationContext()).setShowBezierWave(false)
        refreshLayout.setEnableHeaderTranslationContent(false)
        refreshLayout.isEnableLoadmore = false
        refreshLayout.setPrimaryColors(resources.getColor(R.color.colorPrimaryDark))
    }
    override fun loadData() {
        getLocalData()
        getRemoteData()
    }

    private fun getRemoteData() {
        showLoadingPage()
        mPresenter.getRemoteData(memId,page,rows,type)
    }


    override fun onCheckRemoteData(t: MyVisitorsResp) {
        showContentPage()
        RxBus.get().post(TodayVisitorNumberBean(t.todayVisitors?:0,t.totalVisitors?:0))
        if (t.visitorList != null && t.visitorList.isNotEmpty()) {
            when(page) {
                BaseConstant.ONE-> {

                    recyclerView?.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    adapter = EnterShopAdapter(R.layout.app_item_enter_shop,t.visitorList)
                    recyclerView?.adapter = adapter
                    if (t.visitorList.size >= rows) {
                        adapter?.setOnLoadMoreListener(this, recyclerView)
                    }

                }
                else-> {
                    adapter?.addData(t.visitorList)
                    if (t.visitorList.size >= rows) {
                        adapter?.loadMoreComplete()
                    } else {
                        adapter?.loadMoreEnd()
                    }

                }
            }
        }else {
            when(page) {
                BaseConstant.ONE-> {
                    showEmptyPage()
                }
                else-> {
                    adapter?.loadMoreEnd()
                }
            }
        }
    }

    override fun onLoadMoreRequested() {
        disableRefreshLayout()
        page++
        mPresenter.getRemoteData(memId,page,rows,type)
    }



    private fun getLocalData() {
        memId = AppPrefsUtils.getString("memId")
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun enableRefreshLayout() {
        refreshLayout.isEnableRefresh = true
    }

    override fun disableRefreshLayout() {
        refreshLayout.isEnableRefresh = false
    }

    override fun finishRefreshLayout() {
        refreshLayout.finishRefresh()
    }

    override fun enableLoadMore() {
        adapter?.setEnableLoadMore(true)
    }

    override fun disableLoadMore() {
        adapter?.setEnableLoadMore(false)
    }

    override fun showLoadingPage() {
        multiStateView.startLoading()
    }

    override fun showContentPage() {
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    override fun showEmptyPage() {
        multiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun showErrorPage() {
        multiStateView.viewState = MultiStateView.VIEW_STATE_ERROR

    }


    class EnterShopAdapter(resId: Int, dataList: List<MyVisitorsResp.Visitor?>?) : BaseQuickAdapter<MyVisitorsResp.Visitor?, BaseViewHolder>(resId,dataList) {



        override fun convert(helper: BaseViewHolder?, item: MyVisitorsResp.Visitor?) {
            val portrait = helper?.getView<CircleImageView>(R.id.portrait)
            if(TextUtils.isEmpty(item?.avatar)) {
                portrait?.setImageResource(R.drawable.circleimage)
            }else{
                Glide.with(MyApplication.getApplicationContext())
                        .load(
                                if ((item?.avatar?:"").contains("http"))
                                    item?.avatar
                                else BaseConstant.IMAGE_SERVER_ADDRESS.plus(item?.avatar)
                        )
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.circleimage)
                        .into(portrait)
            }
            helper?.setText(R.id.nickname,item?.nickname?:"匿名用户")
            //访问时间
            val visitTime = item?.visitTime

            if (visitTime != null) {
                helper?.setText(R.id.date, visitTime)
            }

        }

    }
}