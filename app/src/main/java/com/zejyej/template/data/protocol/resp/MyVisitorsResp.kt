package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */

data class MyVisitorsResp(
    @SerializedName("TodayVisitors") val todayVisitors: Int?, //0
    @SerializedName("TotalFans") val totalFans: Int?, //1032
    @SerializedName("VisitorList") val visitorList: List<Visitor?>?,
    @SerializedName("TotalVisitors") val totalVisitors: Int? //8952
) {
    data class Visitor(
        @SerializedName("visitTime") val visitTime: String?, //2018-01-16 11:44:44
        @SerializedName("nickname") val nickname: String?, //
        @SerializedName("avatar") val avatar: String? //https://wx.qlogo.cn/mmopen/x
    )
}