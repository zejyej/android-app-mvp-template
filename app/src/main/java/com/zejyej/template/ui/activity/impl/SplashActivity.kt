package com.zejyej.template.ui.activity.impl

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.zejyej.template.R
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.libselflibrary.net.NetWorkUtils
import com.zejyej.template.ui.activity.ISplashView
import com.zejyej.template.presenter.impl.PSplash
import com.zejyej.template.widget.RxVideoDown
import com.orhanobut.hawk.Hawk
import com.qiyukf.nimlib.sdk.NimIntent


/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
class SplashActivity: BaseModuleMvpActivity<PSplash>(), ISplashView {

    private var hasParseConsult = false
    private var username: String? = null
    private var password: String? = null

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_splash)
        //检查网络飘
        checkNetWork()
        //更替图片
        switchPicture()
        //客服跳转标记
        parseQiyuIntent()
        //检测是否登陆\
        checkLogin()
    }

    private fun checkLogin() {
        username = Hawk.get<String>("username", null)
        password = Hawk.get<String>("password", null)

        if (username == null || password == null) {
            //直接跳登录页面
            onCheckLogin(false)
        } else {
            mPresenter.checkLogin(
                    LoginReq(username!!,password!!)
            )
        }
    }

     override fun onCheckLogin(isLogined: Boolean) {
        if (isLogined) {
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity, MineActivity::class.java)
                intent.putExtra("hasParseConsult", hasParseConsult)
                startActivity(intent)
                finish()
            }, 1000)
        }else {
            Handler().postDelayed({
                //跳登陆页，不打开客服页
                val intent = Intent(this@SplashActivity, VipActivity::class.java)
                intent.putExtra("isRes", 0)
                startActivity(intent)
                finish()
            }, 1000)
        }
    }

    private fun parseQiyuIntent() {
        val intent = intent
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            //是解析客服
            hasParseConsult = true
            // 最好将intent清掉，以免从堆栈恢复时又打开客服窗口
            setIntent(Intent())
        }
    }

    private fun checkNetWork() {
        if (!NetWorkUtils.isNetWorkAvailable(this)) {
            if(!isFinishing) {
                val rxVideoDown = RxVideoDown(this)
                rxVideoDown.setContent("网络未连接\n请检查网络")
                rxVideoDown.show()
            }
        }
    }

    private fun switchPicture() {

    }
}