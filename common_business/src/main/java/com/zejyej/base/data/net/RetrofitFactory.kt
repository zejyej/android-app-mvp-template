package com.zejyej.base.data.net

import com.zejyej.base.common.BaseConstant
import com.zejyej.base.util.AppPrefsUtils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
class RetrofitFactory private constructor(){

    companion object {
        val retrofitInstance:RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    init {

        interceptor = Interceptor {
            chain -> val request = chain.request()
                .newBuilder()
                .addHeader(BaseConstant.CONTENT_TYPE,BaseConstant.APPLICATION_JSON)
                .addHeader(BaseConstant.CHARSET,BaseConstant.UTF8)
                .addHeader(BaseConstant.TABLE_PREFS,AppPrefsUtils.getString(BaseConstant.TOKEN))
                .addHeader(BaseConstant.PHONE,AppPrefsUtils.getString(BaseConstant.PHONE))
                .build()
            chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()

    }

    private fun initClient(): OkHttpClient {
        return if (BaseConstant.isDebug) {
            OkHttpClient.Builder()
                    .addInterceptor(initLogInterceptor())
                    .addInterceptor(interceptor)
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .build()
        }else {
            OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .build()
        }


    }

    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service:Class<T>):T {
        return retrofit.create(service)
    }


}