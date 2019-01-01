package com.zejyej.template.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zejyej.template.data.protocol.resp.TeamInviteResp
import com.zejyej.template.data.protocol.resp.TeamInviteUpgradeSingleResp
import java.io.Serializable

/**
 * @desc
 * @author zejyej
 * @date 2018/5/5
 */
data class TeamInviteDataBean(
        private val itemType: Int,
        val itemTop: TeamInviteResp.TeamInvite ?,
        val itemBottom: TeamInviteUpgradeSingleResp ?
): Serializable, MultiItemEntity {

    companion object {
        val TYPE_HEAD = 1
        val TYPE_TOP = 2
        val TYPE_BOTTOM = 3
    }

    override fun getItemType(): Int {
        return itemType
    }
}