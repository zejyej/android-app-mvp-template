package com.zejyej.template.ui.activity.impl

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.zejyej.base.bus.RxBus
import com.zejyej.template.R
import com.zejyej.template.event.FansNumberBean
import com.zejyej.template.event.TodayVisitorNumberBean
import com.zejyej.template.presenter.impl.PTodayVisitor
import com.zejyej.template.ui.activity.ITodayVisitorView
import com.zejyej.template.ui.fragment.impl.MyFansFragment
import com.zejyej.template.ui.fragment.impl.MyVisitorsFragment
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.app_activity_today_visitor.*

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
class TodayVisitorActivity:BaseModuleMvpActivity<PTodayVisitor>(),ITodayVisitorView {

    private var disposable1: Disposable? = null
    private var disposable2: Disposable?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_today_visitor)
        val fragments  = arrayListOf<Fragment>(
                MyVisitorsFragment(),
                MyFansFragment()

        )
        val titles = arrayListOf(
                "我的访客",
                "我的粉丝"
        )
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager,titles,fragments)


        tabLayout.setupWithViewPager(viewPager)


        disposable1 = RxBus.get()
                .toFlowable(TodayVisitorNumberBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: TodayVisitorNumberBean -> kotlin.run {
                    titles[0] = if(t.totalNumber == 0 && t.todayNumber == 0) "我的访客" else "我的访客(今日${t.todayNumber}/累计${t.totalNumber})"

                    (viewPager.adapter as ViewPagerAdapter).notifyDataSetChanged()
                }
                }, {
                    t: Throwable -> Logger.d("throw:$t")
                })

        disposable2 = RxBus.get()
                .toFlowable(FansNumberBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it -> kotlin.run {
                    titles[1] = if (it.fansNum == 0) "我的粉丝" else "我的粉丝(${it.fansNum})"
                    (viewPager.adapter as ViewPagerAdapter).notifyDataSetChanged()
                }
                },{
                    it -> Logger.d("throw:$it")
                })


        iv_back.setOnClickListener {
            finish()
        }

    }

    override fun injectComponent() {

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

}