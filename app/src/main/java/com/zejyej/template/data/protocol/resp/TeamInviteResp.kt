package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */


data class TeamInviteResp(
    @SerializedName("InviteCount") val inviteCount: Int?, //46
    @SerializedName("TeamInviteCount") val teamInviteCount: Int?, //108
    @SerializedName("teamInviteList") val teamInviteList: List<TeamInvite>?
) {
    data class TeamInvite(
        @SerializedName("account") val account: String?, //15666197283
        @SerializedName("avatar") val avatar: String?, //group1/M00/01/6E/PaD1SFoAMFeAMh6WAAB5ImZP8Sw30.JPEG
        @SerializedName("nickname") val nickname: String?, //
        @SerializedName("memLevel") val memLevel: Int?, //1
        @SerializedName("joinTime") val joinTime: String?, //2017-11-06 18:37:13
        @SerializedName("inviteName") val inviteName: String? //Waiting
    )
}