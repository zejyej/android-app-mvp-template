package com.zejyej.template.ui.activity.impl

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bigkoo.pickerview.OptionsPickerView
import com.zejyej.base.bus.RxBus
import com.zejyej.base.extension.onClick
import com.zejyej.template.R
import com.zejyej.template.common.MyApplication
import com.zejyej.template.data.model.AddressInfo
import com.zejyej.template.event.AddReceiveInfoFinishBean
import com.zejyej.template.injection.component.DaggerUserComponent
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.presenter.impl.PAddReceiveInfo
import com.zejyej.template.ui.activity.IAddReceiveInfoView
import com.zejyej.template.util.GetJsonDataUtil
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.app_add_receive_info.*
import org.json.JSONArray
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * @desc
 * @author zejyej
 * @date 2018/5/7
 */
class AddReceiveInfoActivity:BaseModuleMvpActivity<PAddReceiveInfo>(),IAddReceiveInfoView, View.OnClickListener{


    private val MSG_LOAD_SUCCESS = 0x0002
    private var hasLoaded = false
    private var mDisposable: Disposable? = null
    private var provinceName: String? = null
    private var cityName: String? = null
    private var areaName: String? = null
    //省
    private var provinceItems: ArrayList<AddressInfo> = ArrayList()
    //市
    private var cityItems: ArrayList<ArrayList<AddressInfo.ChildrenCity>> = ArrayList()
    //区
    private var areaItems: ArrayList<ArrayList<ArrayList<AddressInfo.ChildrenCity.ChildrenArea>>> = ArrayList()

    private  val handler  = MyHander(this)

    class MyHander(val activity:AddReceiveInfoActivity): Handler() {

        val weakActivity = WeakReference<AddReceiveInfoActivity>(activity)

        override fun handleMessage(msg: Message?) {
            weakActivity.get()?.let {
                when(msg?.what) {
                    it.MSG_LOAD_SUCCESS -> {
                        it.hasLoaded = true
                    }
                    else -> {

                    }
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_add_receive_info)
        initView()
        iniData()
        parseJson()
        receivePoster()
    }

    private fun receivePoster() {
        mDisposable = RxBus
                .get()
                .toFlowable(AddReceiveInfoFinishBean::class.java)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isFinished) {
                        finish()
                    }
                },{
                    Logger.d("".plus(it.toString()))
                })
    }

    private fun parseJson() {
        Thread(Runnable {
                initJsonData()
        }).run()

    }

    //从asset中读取
    private fun initJsonData() {
        val jsonData = GetJsonDataUtil().getJson(MyApplication.getApplicationContext(), "LAreaData.json")
        //gson转换
        val addressInfoArrayList = parseData(jsonData)
        //省市区
        val areaMap = HashMap<String,String>()
        provinceItems  = addressInfoArrayList

        provinceItems.forEach {

            addressInfo -> kotlin.run {
                //加入省份编码
                areaMap.put(addressInfo.id?:"",addressInfo.name?:"")
                //城市
                val cityList = ArrayList<AddressInfo.ChildrenCity>()
                //该省的所有地区列表（第三级）
                val provinceAreaList = ArrayList<ArrayList<AddressInfo.ChildrenCity.ChildrenArea>>()

                //遍历该省份所有城市
                addressInfo.children?.forEach{

                        childrenCity ->kotlin.run {
                            //加入城市编码
                            areaMap.put(childrenCity.id?:"",childrenCity.name?:"")
                            //添加城市
                            cityList.add(childrenCity)
                            //该城市的所有地区列表
                            val cityAreaList = ArrayList<AddressInfo.ChildrenCity.ChildrenArea>()
                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (childrenCity.children == null || childrenCity.children.isEmpty()) {
                                cityAreaList.add(AddressInfo.ChildrenCity.ChildrenArea("","",null))
                            }else if (childrenCity.children.size == 1) {
                                //首先，加入原有的仅有的一个选项，接着把第四级的提升上来作为补充
                                childrenCity.children.forEach{
                                    childrenArea ->kotlin.run {
                                        areaMap.put(childrenArea.id?:"",childrenArea.name?:"")
                                        //添加该城市所有地区数据
                                    cityAreaList.add(childrenArea)
                                    }
                                }
                                //把只有单个第三级的提升上来
                                childrenCity.children[0].children?.forEach{
                                    childrenRoad ->kotlin.run {
                                        areaMap.put(childrenRoad.id?:"",childrenRoad.name?:"")
                                    cityAreaList.add(AddressInfo.ChildrenCity.ChildrenArea(childrenRoad.id?:"",childrenRoad.name?:"",null))
                                    }


                                }

                            }else {
                                childrenCity.children.forEach{
                                    childrenArea ->kotlin.run {
                                        //加入区域编码（正常的区域编码）
                                        areaMap.put(childrenArea.id?:"",childrenArea.name?:"")
                                        //添加该城市所有地区数据
                                    cityAreaList.add(childrenArea)
                                    }

                                }

                                //添加该省所有地区数据
                                provinceAreaList.add(cityAreaList)
                            }

                            //添加城市数据
                            cityItems.add(cityList)
                            //添加地区数据
                        areaItems.add(provinceAreaList)
                        }

                }
            }

        }
        Hawk.put<Map<String, String>>("addressAreaMap", areaMap)
        Logger.d("provincePre:"+provinceItems[0])
        handler.sendEmptyMessage(MSG_LOAD_SUCCESS)
    }

    private fun parseData(jsonData: String?): ArrayList<AddressInfo> {
        val detail = ArrayList<AddressInfo>()
        try {
            val data = JSONArray(jsonData)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<AddressInfo>(data.optJSONObject(i).toString(), AddressInfo::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return detail
    }

    private fun iniData() {

    }

    private fun initView() {
        etMobile.setText(Hawk.get("username",""))
        ivBack.onClick(this)
        tvSelectProvince.onClick(this)
        tvSelectCity.onClick(this)
        tvSelectArea.onClick(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvSelectProvince-> {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                        llAddress.windowToken, 0
                )
                if (hasLoaded) {
                    showPickerView()
                }
            }

            R.id.tvSelectCity-> {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                        llAddress.windowToken, 0
                )
                if (hasLoaded) {
                    showPickerView()
                }
            }

            R.id.tvSelectArea -> {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                        llAddress.windowToken, 0
                )
                if (hasLoaded) {
                    showPickerView()
                }

            }

        }
    }

    private fun showPickerView() {
        Logger.d("showPickers")
        val pvOptions = OptionsPickerView.Builder(
                this,
                OptionsPickerView.OnOptionsSelectListener {
                    options1, options2, options3, v ->kotlin.run {
                        //拿到数据，code和名称
                        provinceName = provinceItems[options1].name
                        cityName = cityItems[options1][options2].name
                        areaName = areaItems[options1][options2][options3].name
                        //显示
                        tvSelectProvince.text = provinceName
                        tvSelectCity.text = cityName
                        tvSelectArea.text = areaName
                    }

                }
        )
                .setTitleText("城市选择")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build()
        Logger.d("provinceItems:"+provinceItems[0])
        pvOptions.setPicker(provinceItems, cityItems, areaItems)//三级选择器
        pvOptions.show()
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
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable?.dispose()
            mDisposable = null
        }
        super.onDestroy()
    }

}