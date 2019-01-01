package com.zejyej.base.rx

import com.zejyej.base.common.ResultStatus
import com.zejyej.base.data.protocol.resp.BaseResponse
import io.reactivex.Observable
import io.reactivex.functions.Function



/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
class BaseFuncBoolean<T>: Function<BaseResponse<T>, Observable<Boolean>> {
    override fun apply(t: BaseResponse<T>): Observable<Boolean> {
        if (t.code != ResultStatus.CODE_SUCCESS){
            return Observable.error(BaseException(t.code?:0, t.message?:"网络请求失败，请重试"))
        }

        return Observable.just(true)
    }


}