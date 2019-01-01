package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */


data class MyInviteResp(
    @SerializedName("myInviteList") val myInviteList: List<MyInvite>?,
    @SerializedName("InviteCount") val inviteCount: Int? , //46
    @SerializedName("TeamInviteCount") val teamInviteCount: Int?  //108
) {
    data class MyInvite(
        @SerializedName("account") val account: String? , //13958808531
        @SerializedName("memLevel") val memLevel: Int? , //1
        @SerializedName("joinTime") val joinTime: String? , //2017-08-12 10:31:33
        @SerializedName("isPay") val isPay: Int? , //1
        @SerializedName("avatar") val avatar: String? , //group1/M00/00/ED/PaD1SFnbJu-AdajFAAVhQIcey5I268.png
        @SerializedName("nickname") val nickname: String?  //beautiful life
    )
}
