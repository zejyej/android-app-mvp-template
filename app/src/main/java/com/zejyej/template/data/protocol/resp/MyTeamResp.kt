package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */

data class MyTeamResp(
    @SerializedName("TeamNum") val teamNum: Int?, //302
    @SerializedName("TeamLeader") val teamLeader: TeamLeader?,
    @SerializedName("TeamList") val teamList: List<Team?>?
) {
    data class Team(
        @SerializedName("account") val account: String?, //18888742882
        @SerializedName("memLevel") val memLevel: Int?, //1
        @SerializedName("joinTime") val joinTime: String?, //2017-11-14 12:24:56
        @SerializedName("inviteName") val inviteName: String?, //
        @SerializedName("avatar") val avatar: String?, //group1/M00/01/3B/PaD1SFnynxKAS3QiAAA_FYwkbgQ08.JPEG
        @SerializedName("nickname") val nickname: String? //
    )

    data class TeamLeader(
        @SerializedName("account") val account: String?, //18626882219
        @SerializedName("avatar") val avatar: String?, //group1/M00/01/9A/PaD1SFoLFEiAThq0AAF3ulqd8SM925.png
        @SerializedName("nickname") val nickname: String?, //
        @SerializedName("memLevel") val memLevel: Int?, //3
        @SerializedName("wxAccount") val wxAccount: String? //
    )
}