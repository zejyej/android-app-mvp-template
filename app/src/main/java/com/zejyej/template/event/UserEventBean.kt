package com.zejyej.template.event

import com.zejyej.base.bus.IBus

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */


//个人邀请数量
data class MyInviteNumBean(val inviteNum: Int): IBus.IEvent {
    override fun getTag(): Int {
        return 22581
    }

}

//团队邀请数量
data class TeamInviteNumBean(val inviteNum: Int): IBus.IEvent {
    override fun getTag(): Int {
        return 22582
    }
}

//今日访客数量
data class TodayVisitorNumberBean (val todayNumber:Int,val totalNumber:Int): IBus.IEvent {
    override fun getTag(): Int {
        return 1765
    }
}

//我的粉丝数量
data class FansNumberBean(val fansNum:Int):IBus.IEvent {
    override fun getTag(): Int {
        return 9992
    }
}

data class FinishBean(val isFinish: Boolean) : IBus.IEvent {
    override fun getTag(): Int {
        return 1
    }

}

data class AddReceiveInfoFinishBean(val isFinished: Boolean) : IBus.IEvent {
    override fun getTag(): Int {
        return 853
    }
}
