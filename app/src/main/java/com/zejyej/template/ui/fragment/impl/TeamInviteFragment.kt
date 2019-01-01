package com.zejyej.template.ui.fragment.impl

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zejyej.base.bus.RxBus
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.extension.startLoading
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.model.TeamInviteDataBean
import com.zejyej.template.data.protocol.resp.TeamInviteResp
import com.zejyej.template.data.protocol.resp.TeamInviteUpgradeSingleResp
import com.zejyej.template.event.MyInviteNumBean
import com.zejyej.template.event.TeamInviteNumBean
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PTeamInvite
import com.zejyej.template.ui.fragment.ITeamInviteView
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.header.MaterialHeader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.app_fragment_team_invite.*
import java.util.*


/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class TeamInviteFragment:BaseModuleMvpFragment<PTeamInvite>(),ITeamInviteView {



    private lateinit var memId: String
    private var pageUp: Int = BaseConstant.ONE
    private var pageBelow: Int = BaseConstant.ONE
    private val rows: Int = BaseConstant.TEN
    private var adapter1: TeamInviteAdapter? = null
    private var allDatas: ArrayList<TeamInviteDataBean> =  arrayListOf()
    private var topEmpty: Boolean = false
    private var bottomEmpty: Boolean = false
    private var isFirstInit: Boolean = true
    private val type = BaseConstant.teamInvite

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.app_fragment_team_invite, container, false)
    }



    override fun initView() {
        configRefreshLayout()
    }

    private fun configRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            disableRefreshLayout()
            adapter1?.setEnableLoadMore(false)
            recyclerView?.scrollToPosition(BaseConstant.ZERO)
            allDatas.clear()
            adapter1?.setNewData(allDatas)
            pageUp = BaseConstant.ONE
            pageBelow = BaseConstant.ONE
            topEmpty = false
            bottomEmpty = false
            isFirstInit =  true
            mPresenter.getRemoteDataFirst(memId,pageUp,rows,type)

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
        mPresenter.getRemoteDataFirst(memId,pageUp,rows,type)
    }


    override fun onCheckRemoteDataFirst(t: TeamInviteResp) {
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        RxBus.get().post(MyInviteNumBean(t.inviteCount?:0))
        RxBus.get().post(TeamInviteNumBean(t.teamInviteCount?:0))
        if(t.teamInviteList != null && t.teamInviteList.isNotEmpty()) {

            val tops = arrayListOf<TeamInviteDataBean>()

            for (index in t.teamInviteList.indices) {
                tops.add(TeamInviteDataBean(TeamInviteDataBean.TYPE_TOP,t.teamInviteList[index],null))

            }

            when(pageUp) {
                BaseConstant.ONE -> {

                    recyclerView.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    allDatas.addAll(tops)
                    adapter1 = TeamInviteAdapter(allDatas)
                    recyclerView.adapter = adapter1
                    if (pageUp*rows < (t.teamInviteCount?:0)) {
                        adapter1?.setOnLoadMoreListener({
                                disableRefreshLayout()
                                pageUp++
                                mPresenter.getRemoteDataFirst(memId,pageUp,rows,type)
                            }, recyclerView)

                    }else {
                        mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                    }

                }

                else-> {
                    adapter1?.addData(tops)
                    if (pageUp*rows < (t.teamInviteCount?:0)) {
                        adapter1?.loadMoreComplete()
                    } else {
                        mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                    }

                }
            }
        }else {
            when (pageUp) {
                1 -> {

                    recyclerView?.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    adapter1 = TeamInviteAdapter(null)
                    recyclerView?.adapter = adapter1
                    topEmpty = true
                    mPresenter.getRemoteDataSecond(memId,pageBelow, rows)

                }
                else -> {
                    mPresenter.getRemoteDataSecond(memId, pageBelow, rows)
                }
            }

        }
    }


    override fun onCheckRemoteDataSecond(t: List<TeamInviteUpgradeSingleResp>?) {

        if (t != null && t.isNotEmpty()) {
            //datas-bottom
            val bottoms  = arrayListOf<TeamInviteDataBean>()
            for (index in t.indices) {
                bottoms.add(TeamInviteDataBean(TeamInviteDataBean.TYPE_BOTTOM,null,t[index]))
            }

            when(pageBelow) {

                BaseConstant.ONE -> {

                    //add head
                    if (isFirstInit) {
                        adapter1?.addData(TeamInviteDataBean(TeamInviteDataBean.TYPE_HEAD,null,null))
                        isFirstInit = false
                    }

                    //add data
                    adapter1?.addData(bottoms)

                    if (t.size >= rows ) {
                        adapter1?.setOnLoadMoreListener({
                            disableRefreshLayout()
                            pageBelow++
                            mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                            },recyclerView)

                    }else {
                        adapter1?.loadMoreEnd()
                    }


                }

                else -> {
                    adapter1?.addData(bottoms)
                    if (t.size >= rows ) {
                        adapter1?.loadMoreComplete()
                    } else {
                        adapter1?.loadMoreEnd()
                    }
                }
            }


        }else {
            when(pageBelow) {
                BaseConstant.ONE -> {
                    bottomEmpty = true
                    if (topEmpty && bottomEmpty) {
                        multiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
                    }else {
                        adapter1?.loadMoreEnd()
                    }
                }
                else-> {
                    adapter1?.loadMoreEnd()

                }
            }
        }
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

    class TeamInviteAdapter(dataList: ArrayList<TeamInviteDataBean>?): BaseMultiItemQuickAdapter<TeamInviteDataBean, BaseViewHolder>(dataList) {

        init {
            addItemType(TeamInviteDataBean.TYPE_TOP,R.layout.app_item_team_invite)
            addItemType(TeamInviteDataBean.TYPE_HEAD,R.layout.app_item_team_invite_upgrade_head)
            addItemType(TeamInviteDataBean.TYPE_BOTTOM,R.layout.app_item_team_invite_upgrade)
        }




        override fun convert(helper: BaseViewHolder?, item: TeamInviteDataBean?) {
            when(helper?.itemViewType) {

                TeamInviteDataBean.TYPE_TOP -> {
                    item?.itemTop?.let {
                        val portrait = helper.getView<CircleImageView>(R.id.portrait)
                        if ((it.avatar?:"").contains("http")) {
                            Glide.with(MyApplication.getApplicationContext())
                                    .load(it.avatar)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .error(R.drawable.circleimage)
                                    .into(portrait)
                        }else {
                            Glide.with(MyApplication.getApplicationContext())
                                    .load(BaseConstant.IMAGE_SERVER_ADDRESS.plus(it.avatar))
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .error(R.drawable.circleimage)
                                    .into(portrait)
                        }

                        helper.setText(R.id.nickname,it.nickname?:"空昵称")
                                .setText(R.id.tvAccount,"".plus(it.account?:""))
                                .setText(R.id.tvDate,"".plus(if (it.joinTime == null) "未付费" else it.joinTime))
                                .setText(R.id.tvMemLevel,"".plus(when(it.memLevel) {3 -> "生活专家" 2 -> "金牌测评师" else -> "测评师"}))
                                .setText(R.id.tvInvitorPeople,"".plus(it.inviteName?:""))
                    }
                }

                TeamInviteDataBean.TYPE_HEAD -> {

                }

                TeamInviteDataBean.TYPE_BOTTOM -> {
                    item?.itemBottom?.let {
                        val portrait = helper.getView<CircleImageView>(R.id.portrait)
                        if ((it.avatar?:"").contains("http")) {
                            Glide.with(MyApplication.getApplicationContext())
                                    .load(it.avatar)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .error(R.drawable.circleimage)
                                    .into(portrait)
                        }else {
                            Glide.with(MyApplication.getApplicationContext())
                                    .load(BaseConstant.IMAGE_SERVER_ADDRESS.plus(it.avatar))
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .error(R.drawable.circleimage)
                                    .into(portrait)
                        }
                        helper.setText(R.id.nickname,it.nickname?:"空昵称")
                                .setText(R.id.tvAccount,"".plus(it.account?:""))
                                .setText(R.id.tvDate,"".plus(if (it.joinTime == null) "未付费" else it.joinTime))
                                .setText(R.id.tvMemLevel,"".plus(when(it.memLevel) {3 -> "生活专家" 2 -> "金牌测评师" else -> "测评师"}))
                                .setText(R.id.tvTeamNum,"".plus(it.teamNum?:0))

                    }
                }

            }

        }

    }


}