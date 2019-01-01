package com.zejyej.template.ui.activity.impl

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.zejyej.base.bus.RxBus
import com.zejyej.template.R
import com.zejyej.template.event.MyInviteNumBean
import com.zejyej.template.event.TeamInviteNumBean
import com.zejyej.template.presenter.impl.PInvite
import com.zejyej.template.ui.activity.IInviteView
import com.zejyej.template.ui.fragment.impl.MyInviteFragment
import com.zejyej.template.ui.fragment.impl.TeamInviteFragment
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class MyInviteActivity: BaseModuleMvpActivity<PInvite>(),IInviteView {

    private var disposable1: Disposable? = null
    private var disposable2: Disposable?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_my_invite)
        val fragments  = arrayListOf<Fragment>(
                MyInviteFragment(),
                TeamInviteFragment()
        )
        val titles = arrayListOf(
                "我的邀请",
                "团队邀请"
        )
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, titles, fragments)
        tabLayout.setupWithViewPager(viewPager)
        disposable1 = RxBus.get()
                .toFlowable(MyInviteNumBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it -> kotlin.run {
                    if (it.inviteNum != 0) {
                        titles[0] = "我的邀请".plus("(").plus(it.inviteNum).plus(")")
                    }else {
                        titles[0] = "我的邀请"
                    }
                    (viewPager.adapter as ViewPagerAdapter).notifyDataSetChanged()

                }
                },{
                    it -> Logger.d("throw:$it")
                })

        disposable2 = RxBus.get()
                .toFlowable(TeamInviteNumBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it -> kotlin.run {
                    if (it.inviteNum != 0) {
                        titles[1] = "团队邀请".plus("(").plus(it.inviteNum).plus(")")
                    }else {
                        titles[1] = "团队邀请"
                    }
                    (viewPager.adapter as ViewPagerAdapter).notifyDataSetChanged()
                }
                },{
                    it -> Logger.d("throw:$it")
                })
    }


    class ViewPagerAdapter(
            fm: FragmentManager?,
            private var titles: ArrayList<String>,
            private var fragments: ArrayList<Fragment>
    ): FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return titles.size
        }


        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

    }


    override fun onDestroy() {
        if(disposable1!=null && !disposable1!!.isDisposed) {
            disposable1!!.dispose()
            disposable1 = null
        }
        if(disposable2!=null && !disposable2!!.isDisposed) {
            disposable2!!.dispose()
            disposable2 = null
        }
        super.onDestroy()
    }

    override fun injectComponent() {

    }
}