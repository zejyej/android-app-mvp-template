package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */

data class MyTeamUpgradeResp(
    @SerializedName("TotalCount") val totalCount: Int?, //2
    @SerializedName("UpGradeList") val upGradeList: List<UpGrade?>?
) {
    data class UpGrade(
        @SerializedName("account") val account: String?, //13587591264
        @SerializedName("avatar") val avatar: String?, //group1/M00/02/81/rBCXMlpFAsaAfA9DAAteEdbQ_3c169.png
        @SerializedName("nickname") val nickname: String?, //
        @SerializedName("inviteName") val inviteName: String?, //
        @SerializedName("teamNum") val teamNum: Int? //0
    )
}