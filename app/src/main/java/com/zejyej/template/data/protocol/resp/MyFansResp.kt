package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */

data class MyFansResp(
    @SerializedName("TodayVisitors") val todayVisitors: Int?, //0
    @SerializedName("TotalFans") val totalFans: Int?, //1032
    @SerializedName("fansList") val fansList: List<Fans?>?,
    @SerializedName("TotalVisitors") val totalVisitors: Int? //8952
) {
    data class Fans(
        @SerializedName("nickname") val nickname: String?, //
        @SerializedName("avatar") val avatar: String?, //https://wx.qlogo.cn/mmopen/vi_32/x
        @SerializedName("memId") val memId: String? //51399
    )
}