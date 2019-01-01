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
import com.zejyej.template.data.protocol.resp.MyFansResp
import com.zejyej.template.event.FansNumberBean
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PMyFans
import com.zejyej.template.ui.fragment.IMyFansView
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.header.MaterialHeader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.app_fragment_my_fans.*
import java.math.BigDecimal

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class MyFansFragment:BaseModuleMvpFragment<PMyFans>(),IMyFansView, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var memId: String
    private var page: Int = BaseConstant.ONE
    private val rows: Int = BaseConstant.TEN
    private var adapter: FansAdapter? = null
    private val type = BaseConstant.fans

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.app_fragment_my_fans,container,false)
    }

    override fun initView() {
        configRefreshLayout()
    }

    private fun configRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            refreshLayout.isEnableRefresh = false
            adapter?.setEnableLoadMore(false)
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
        multiStateView.startLoading()
        mPresenter.getRemoteData(memId,page,rows,type)
    }

    override fun onCheckRemoteData(t: MyFansResp) {
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        RxBus.get().post(FansNumberBean(t.totalFans?:0))
        if(t.fansList!=null && t.fansList.isNotEmpty()) {
            when(page) {
                BaseConstant.ONE -> {
                    recyclerView.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    adapter = FansAdapter(R.layout.app_item_my_fans, t.fansList)
                    recyclerView.adapter = adapter
                    if (t.fansList.size >= rows) {
                        if (recyclerView != null) {
                            adapter?.setOnLoadMoreListener(this, recyclerView)
                        }

                    }

                }
                else-> {
                    adapter?.addData(t.fansList)
                    if (t.fansList.size >= rows) {
                        adapter?.loadMoreComplete()
                    } else {
                        adapter?.loadMoreEnd()
                    }

                }
            }
        }else{
            when(page) {
                BaseConstant.ONE -> {
                    multiStateView.viewState  = MultiStateView.VIEW_STATE_EMPTY
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

    override fun showErrorPage() {
        multiStateView.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    class FansAdapter(resId: Int,dataList: List<MyFansResp.Fans?>): BaseQuickAdapter<MyFansResp.Fans?, BaseViewHolder>(resId,dataList) {

        override fun convert(helper: BaseViewHolder?, item: MyFansResp.Fans?) {
            val portrait = helper?.getView<CircleImageView>(R.id.portrait)
            if(TextUtils.isEmpty(item?.avatar)) {
                portrait?.setImageResource(R.drawable.circleimage)
            }else{
                Glide.with(MyApplication.getApplicationContext())
                        .load(if ((item?.avatar?:"").contains("http"))item?.avatar else BaseConstant.IMAGE_SERVER_ADDRESS.plus(item?.avatar))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.circleimage)
                        .into(portrait)
            }

            helper?.setText(R.id.nickname,item?.nickname?:"匿名用户")
                    ?.setText(R.id.tvTotalVisitNum,"".plus(0))
                    ?.setText(R.id.tvOrderNum,"".plus(0))
                    ?.setText(R.id.tvPuchaseNum,"".plus(BigDecimal.ZERO))

        }

    }


}