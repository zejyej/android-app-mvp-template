package com.zejyej.template.presenter.impl

import android.text.TextUtils
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zejyej.base.common.BaseConstant
import com.zejyej.base.extension.excute
import com.zejyej.base.presenter.impl.BasePresenter
import com.zejyej.base.rx.BaseObserver
import com.zejyej.base.util.AppPrefsUtils
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.data.protocol.resp.LoginResp
import com.zejyej.template.presenter.IPLogin
import com.zejyej.template.service.UserService
import com.zejyej.template.ui.activity.ILoginView
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import com.qiyukf.unicorn.api.RequestCallback
import com.qiyukf.unicorn.api.Unicorn
import com.qiyukf.unicorn.api.YSFUserInfo
import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class PLogin @Inject constructor():BasePresenter<ILoginView>(),IPLogin {

    @Inject
    lateinit var userService: UserService

    override fun login(req: LoginReq) {

        userService.login(req).excute(object : BaseObserver<LoginResp>(mView) {

            override fun onNext(t: LoginResp) {
                //store data
                Hawk.delete("packSellBean")
                Hawk.delete("goodsSellBean")
                Hawk.put<String>("username", t.memAccount?:"")
                Hawk.put<String>("password", req.memPassword)
                AppPrefsUtils.putString("token",t.token?:"")
                AppPrefsUtils.putString("phone",t.memAccount?:"")
                AppPrefsUtils.putString("memId",t.id?:"")
                AppPrefsUtils.putInt("memLevel",t.memLevel?:0)
                AppPrefsUtils.remove("nickname")
                AppPrefsUtils.remove("photoNetUrl")

                //init qiyukf
                val ysfUserInfo = YSFUserInfo()
                ysfUserInfo.userId = t.id
                val memLevel = when (t.memLevel) {
                    BaseConstant.THREE -> "生活专家"
                    BaseConstant.TWO -> "金牌测评师"
                    else -> "测评师"
                }
                val nickName = if (TextUtils.isEmpty(t.nickname)) {
                    t.memAccount?:""
                } else {
                    t.nickname?:""
                }
                ysfUserInfo.data = userInfoData(
                        nickName,
                        t.memAccount?:"",
                        "",
                        memLevel
                ).toJSONString()
                Unicorn.setUserInfo(ysfUserInfo, object : RequestCallback<Void> {
                    override fun onSuccess(param: Void) {
                        Logger.d("切换成功")
                    }

                    override fun onFailed(code: Int) {
                        Logger.d("切换失败")
                    }

                    override fun onException(exception: Throwable) {
                        Logger.d("切换异常")
                    }
                })

                //startActivity
                mView.intentForMineActivity()
            }

        },lifecycleProvider)


    }

    private fun userInfoDataItem(key: String, value: Any, hidden: Boolean, index: Int, label: String?, href: String?): JSONObject {
        val item = JSONObject()
        item["key"] = key
        item["value"] = value
        if (hidden) {
            item["hidden"] = true
        }
        if (index >= 0) {
            item["index"] = index
        }
        if (!TextUtils.isEmpty(label)) {
            item["label"] = label
        }
        if (!TextUtils.isEmpty(href)) {
            item["href"] = href
        }
        return item
    }

    private fun userInfoData(name: String, mobile: String, email: String, memLevel: String): JSONArray {
        val array = JSONArray()
        array.add(userInfoDataItem("real_name", name, false, -1, null, null)) // name
        array.add(userInfoDataItem("mobile_phone", mobile, false, -1, null, null)) // mobile
        array.add(userInfoDataItem("email", email, false, -1, null, null))
        array.add(userInfoDataItem("mem_level", memLevel, false, -1, "会员等级", null)) //头像
        return array
    }



}