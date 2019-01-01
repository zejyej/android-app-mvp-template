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
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.extension.startLoading
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.protocol.resp.MyInviteResp
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PMyInvite
import com.zejyej.template.ui.fragment.IMyInviteView
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.header.MaterialHeader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.app_fragment_my_invite.*

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class MyInviteFragment:BaseModuleMvpFragment<PMyInvite>(), IMyInviteView, BaseQuickAdapter.RequestLoadMoreListener {


    private lateinit var memId: String
    private var page: Int = BaseConstant.ONE
    private val rows: Int = BaseConstant.TEN
    private var adapter: MyInviteAdapter? = null
    private val type = BaseConstant.myInvite
    private val memIdStr = BaseConstant.memIdStr

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.app_fragment_my_invite, container, false)
    }

    override fun handleRemoteData(myInviteList: List<MyInviteResp.MyInvite>?) {
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        if (myInviteList != null && myInviteList.isNotEmpty()) {
            when(page) {
                BaseConstant.ONE -> {
                    refreshLayout.visibility = View.VISIBLE
                    recyclerView.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    adapter = MyInviteAdapter(R.layout.app_item_my_invite, myInviteList)
                    recyclerView.adapter = adapter
                    if (myInviteList.size >= rows) {
                        adapter?.setOnLoadMoreListener(this, recyclerView)
                    }
                }

                else -> {
                    adapter?.addData(myInviteList)
                    if (myInviteList.size >= rows) {
                        adapter?.loadMoreComplete()
                    } else {
                        adapter?.loadMoreEnd()
                    }
                }
            }
        }else {
            when(page) {
                BaseConstant.ONE -> {
                    multiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
                }
                else -> {
                    adapter?.loadMoreEnd()
                }
            }
        }
    }

   override fun loadData() {
        getLocalData()
        getRemoteData()
    }

    private fun getRemoteData() {
        multiStateView.startLoading()
        mPresenter.getRemoteData(memId, page, rows, type)
    }

    private fun getLocalData() {
        memId = AppPrefsUtils.getString(memIdStr)
    }

    override fun initView() {
        configRefreshLayout()
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
    private fun configRefreshLayout() {
        refreshLayout.setOnRefreshListener{
            disableRefreshLayout()
            adapter?.setEnableLoadMore(false)
            recyclerView?.scrollToPosition(BaseConstant.ZERO)
            page = BaseConstant.ONE
            mPresenter.getRemoteData(memId,page,rows,type)
        }
        //set RefreshLayout
        refreshLayout.refreshHeader = MaterialHeader(MyApplication.getApplicationContext()).setShowBezierWave(false)
        refreshLayout.setEnableHeaderTranslationContent(false)
        refreshLayout.isEnableLoadmore = false
        refreshLayout.setPrimaryColors(resources.getColor(R.color.colorPrimaryDark))
    }

    override fun onLoadMoreRequested() {
        disableRefreshLayout()
        page++
        mPresenter.getRemoteData(memId,page,rows,type)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }


    class MyInviteAdapter(resId: Int,dataList: List<MyInviteResp.MyInvite>): BaseQuickAdapter<MyInviteResp.MyInvite?, BaseViewHolder>(resId,dataList){

        override fun convert(helper: BaseViewHolder?, item: MyInviteResp.MyInvite?) {
            val portrait = helper?.getView<CircleImageView>(R.id.portrait)
            if(TextUtils.isEmpty(item?.avatar)) {
                portrait?.setImageResource(R.drawable.circleimage)
            }else{
                Glide.with(MyApplication.getApplicationContext())
                        .load(
                                if ((item?.avatar?:"").contains("http")){
                                      item?.avatar
                                }else {
                                      BaseConstant.IMAGE_SERVER_ADDRESS.plus(item?.avatar)
                                }

                        )
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.circleimage)
                        .into(portrait)

            }
            helper?.setText(R.id.tvAccount,item?.account?:"")
                    ?.setText(R.id.nickname,item?.nickname?:"空昵称")

            //加入时间
            //访问时间
            val joinTime  = item?.joinTime

            if (joinTime != null) {
                helper?.setText(R.id.tvDate, item.joinTime)
            }else {
                helper?.setText(R.id.tvDate, "未付费")
            }

        }


    }

}