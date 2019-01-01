package com.zejyej.template.data.protocol.resp
import com.google.gson.annotations.SerializedName


/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */

data class TeamInviteUpgradeSingleResp(
    @SerializedName("account") val account: String?,
    @SerializedName("memLevel") val memLevel: Int?,
    @SerializedName("joinTime") val joinTime: String?,
    @SerializedName("upgradeTime") val upgradeTime: String?,
    @SerializedName("isPay") val isPay: Int?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("teamNum") val teamNum:Int?
)