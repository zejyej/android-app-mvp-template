package com.zejyej.template.ui.activity.impl

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zejyej.base.bus.RxBus
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.R
import com.zejyej.template.event.FinishBean
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PVip
import com.zejyej.template.ui.activity.IVipView
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.app_activity_vip.*
import org.jetbrains.anko.startActivity

/**
 * @desc
 * @author zejyej
 * @date 2018/5/7
 */
class VipActivity: BaseModuleMvpActivity<PVip>(),IVipView {

    private var phone: String? = null
    private var token: String? = null
    private var memId: String? = null

    private var mSubscribe: Disposable? = null
    //是否注册过，0未注册过---只有Splash能进,跳注册  1-注册过，其他页面997拦截到，跳下单页
    private var isRes: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_vip)
        getLocalData()
        configWebView()
        setClick()
        receivePost()
    }

    private fun receivePost() {
        mSubscribe = RxBus.get().toFlowable(FinishBean::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ finishBean ->
                    if (finishBean.isFinish) {
                        finish()
                    }
                }) { throwable -> com.orhanobut.logger.Logger.d("throw:" + throwable.toString()) }
    }

    private fun setClick() {
        btnLogin.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        Logger.d("isRes:$isRes")
        webView.loadUrl(BaseConstant.WEBPAGE_SERVER_ADDRESS.plus("efguc/attractInvestment?code=&isRes=".plus(isRes)))
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                showLoading()
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                hideLoading()
            }


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                com.orhanobut.logger.Logger.d("url:$url")
                if(url != null && !TextUtils.isEmpty(url)) {
                    if (url.indexOf("packKey=") > 0) {
                        val type = url.split("packKey=")[1].split("&")[0].trim()
                        com.orhanobut.logger.Logger.d("type=$type")
                        AppPrefsUtils.putString("vipType", type)
                        if (isRes == 0) {
                            val intent = Intent(this@VipActivity,RegisterActivity::class.java)
                            startActivity(intent)
                        }else {
                            val intent = Intent(this@VipActivity,AddReceiveInfoActivity::class.java)
                            startActivity(intent)
                        }

                        return true
                    }
                }

                return false
            }
        }

    }

    private fun getLocalData() {
        phone = AppPrefsUtils.getString("phone")
        token = AppPrefsUtils.getString("token")
        memId = AppPrefsUtils.getString("memId")
        isRes = intent?.getIntExtra("isRes",1)?:1
    }




    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }


    override fun onDestroy() {
        if (mSubscribe != null && !mSubscribe!!.isDisposed()) {
            mSubscribe!!.dispose()
        }
        super.onDestroy()
    }

}