package com.zejyej.template.ui.activity.impl

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.extension.startLoading
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.model.TeamDataBean
import com.zejyej.template.data.protocol.resp.MyTeamResp
import com.zejyej.template.data.protocol.resp.MyTeamUpgradeResp
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PMyTeam
import com.zejyej.template.ui.activity.IMyTeamView
import com.zejyej.template.widget.RxVideoDown
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.app_activity_new_my_team.*
import java.math.BigDecimal
import java.util.*

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class MyTeamActivity:BaseModuleMvpActivity<PMyTeam>(),IMyTeamView {

    private lateinit var memId: String
    private var pageUp: Int = BaseConstant.ONE
    private var pageBelow: Int = BaseConstant.ONE
    private val rows: Int = BaseConstant.TEN
    private var adapter1: NewTeamAdapter? = null
    private var allDatas: ArrayList<TeamDataBean> =  arrayListOf()
    private var topEmpty: Boolean = false
    private var bottomEmpty: Boolean = false
    private var isFirstFirstInit = true
    private var isFirstInit: Boolean = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_new_my_team)
        initView()
        loadData()
    }

    private fun loadData() {
        getLocalData()
        getRemoteData()
    }

    private fun getRemoteData() {
        multiStateView.startLoading()
        mPresenter.getRemoteDataFirst(memId,pageUp,rows)
    }

    override fun onCheckRemoteDataFirst(t: MyTeamResp) {
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        //团队信息
        if (t.teamLeader != null && isFirstFirstInit) {
            val teamLeader = t.teamLeader
            tvMiddle.text = if(TextUtils.isEmpty(teamLeader.nickname)) "团队" else (teamLeader.nickname?:"").plus("的团队")
            tvTeamName.text = "团队：".plus(teamLeader.nickname?:"").plus("社群")
            tvAssistant.text = "队长：".plus(teamLeader.nickname?:"")
            tvMemLevel.text = "等级：".plus(
                    when(teamLeader.memLevel) {
                        BaseConstant.THREE -> "生活专家"
                        BaseConstant.TWO -> "金牌测评师"
                        else -> "测评师"
                    }
            )
            tvWechatNum.text = "联系微信：".plus(teamLeader.wxAccount?:"")
            btnClickToCopy.visibility = if (TextUtils.isEmpty(teamLeader.wxAccount)) View.GONE else View.VISIBLE
            btnClickToCopy.setOnClickListener {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.text = teamLeader.wxAccount?:""
                val rxVideoDown = RxVideoDown(this@MyTeamActivity)
                rxVideoDown.setContent("微信号已复制")
                rxVideoDown.show()
            }
            tvTeamPeopleNum.text = "成员数：".plus(t.teamNum?:0).plus("人")
            if (myPic != null) {
                Glide.with(MyApplication.getApplicationContext())
                        .load(BaseConstant.IMAGE_SERVER_ADDRESS.plus(teamLeader.avatar?:""))
                        .error(R.drawable.circleimage)
                        .into(myPic)
            }
            isFirstFirstInit = false
        }



        if(t.teamList != null && t.teamList.isNotEmpty()) {

            val tops = arrayListOf<TeamDataBean>()

            for (index in t.teamList.indices) {
                tops.add(TeamDataBean(TeamDataBean.TYPE_TOP,t.teamList[index],null))

            }

            when(pageUp) {
                BaseConstant.ONE -> {

                    recyclerView.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    allDatas.addAll(tops)
                    adapter1 = NewTeamAdapter(allDatas)
                    recyclerView?.adapter = adapter1
                    if ( t.teamList.size >= rows) {

                        adapter1?.setOnLoadMoreListener({
                                disableRefreshLayout()
                                pageUp++
                                mPresenter.getRemoteDataFirst(memId,pageUp,rows)

                            }, recyclerView)


                    }else {
                        mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                    }

                }

                else-> {
                    adapter1?.addData(tops)
                    if (t.teamList.size >= rows) {
                        adapter1?.loadMoreComplete()
                    } else {
                        mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                    }

                }
            }
        }else{
            when(pageUp) {
                BaseConstant.ONE -> {

                    recyclerView?.layoutManager = LinearLayoutManager(
                            MyApplication.getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
                    adapter1 = NewTeamAdapter(null)
                    recyclerView?.adapter = adapter1
                    topEmpty = true
                    mPresenter.getRemoteDataSecond(memId,pageBelow,rows)

                }
                else-> {
                    mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                }
            }
        }

    }

    override fun onCheckRemoteDataSecond(t: MyTeamUpgradeResp) {
        if (t.upGradeList != null && t.upGradeList.isNotEmpty()) {
            //datas-bottom
            val bottoms  = arrayListOf<TeamDataBean>()
            for (index in t.upGradeList.indices) {
                bottoms.add(TeamDataBean(TeamDataBean.TYPE_BOTTOM,null,t.upGradeList[index]))
            }

            when(pageBelow) {

                BaseConstant.ONE -> {

                    //add head
                    if (isFirstInit) {
                        adapter1?.addData(TeamDataBean(TeamDataBean.TYPE_HEAD,null,null))
                        isFirstInit = false
                    }

                    //add data
                    adapter1?.addData(bottoms)

                    if ( t.upGradeList.size >= rows) {
                        if (recyclerView != null) {
                            adapter1?.setOnLoadMoreListener({
                                disableRefreshLayout()
                                pageBelow++
                                mPresenter.getRemoteDataSecond(memId,pageBelow,rows)
                            },recyclerView)
                        }
                    }else {
                        adapter1?.loadMoreEnd()
                    }


                }

                else -> {
                    adapter1?.addData(bottoms)
                    if ( t.upGradeList.size >= rows) {
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
                else -> {
                    adapter1?.loadMoreEnd()
                }
            }

        }
    }



    private fun getLocalData() {
        memId = AppPrefsUtils.getString("memId")
    }

    private fun initView() {
        configRefreshLayout()
    }

    private fun configRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            disableRefreshLayout()
            adapter1?.setEnableLoadMore(false)
            recyclerView?.scrollToPosition(0)
            allDatas.clear()
            adapter1?.setNewData(allDatas)
            pageUp = BaseConstant.ONE
            pageBelow = BaseConstant.ONE
            topEmpty= false
            bottomEmpty = false
            isFirstFirstInit = true
            isFirstInit = true
            mPresenter.getRemoteDataFirst(memId,pageUp,rows)

        }
        //set RefreshLayout
        refreshLayout.refreshHeader = MaterialHeader(MyApplication.getApplicationContext()).setShowBezierWave(false)
        refreshLayout.setEnableHeaderTranslationContent(false)
        refreshLayout.isEnableLoadmore = false
        refreshLayout.setPrimaryColors(resources.getColor(R.color.colorPrimaryDark))
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


    class NewTeamAdapter(dataList: ArrayList<TeamDataBean>?): BaseMultiItemQuickAdapter<TeamDataBean, BaseViewHolder>(dataList) {

        init {
            addItemType(TeamDataBean.TYPE_TOP, R.layout.app_item_new_my_team)
            addItemType(TeamDataBean.TYPE_HEAD, R.layout.app_item_new_my_team_upgrade_head)
            addItemType(TeamDataBean.TYPE_BOTTOM, R.layout.app_item_new_my_team_upgrade)
        }



        override fun convert(helper: BaseViewHolder?, item: TeamDataBean?) {
            when(helper?.itemViewType) {

                TeamDataBean.TYPE_TOP -> {
                    item?.itemTop?.let {
                        helper.setText(R.id.tvNickName,it.nickname?:"空昵称")
                                .setText(R.id.tvAccount,it.account?:"")
                                .setText(R.id.tvInvitePeople,it.inviteName?:"")
                                .setText(R.id.tvMemLevel,when(it.memLevel) {
                                    3 -> "生活专家"
                                    2 -> "金牌测评师"
                                    else -> "测评师"
                                })
                                .setText(R.id.tvSellNum,"￥".plus( BigDecimal.ZERO))
                                .setText(R.id.tvDate,if (it.joinTime != null) it.joinTime else "")
                    }
                }

                TeamDataBean.TYPE_HEAD -> {

                }

                TeamDataBean.TYPE_BOTTOM -> {
                    item?.itemBottom?.let {
                        helper.setText(R.id.tvMemLevel,when(3) {
                            3 -> "生活专家"
                            2 -> "金牌测评师"
                            else -> "测评师"
                        })
                                .setText(R.id.tvNickName,it.nickname?:"空昵称")
                               // .setText(R.id.tvDate,if (it.joinTime != null) it.joinTime else "")
                                .setText(R.id.tvTeamPeopleNum,"".plus(it.teamNum?:0))
                                .setText(R.id.tvInvitePeople,it.inviteName?:"")
                    }
                }
            }
        }


    }
}