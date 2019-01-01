package com.zejyej.template.service

import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.data.protocol.resp.*
import io.reactivex.Observable


/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
interface UserService {

    //注册
    fun register(req: RegisterReq): Observable<RegisterResp>

    //登陆
    fun login(req: LoginReq): Observable<LoginResp>

    //获取验证码
    fun verifyCode(req: VerifyCodeReq): Observable<Boolean>

    //修改密码
    fun resetPassword(req: ResetPasswordReq): Observable<Boolean>

    //我的邀请
    fun myInvite(
            memId: String,
            page:Int,
            rows:Int,
            type:String
    ): Observable<MyInviteResp>

    //我的邀请-团队邀请、
    fun teamInvite(
            memId: String,
            page:Int,
            rows:Int,
            type:String
    ): Observable<TeamInviteResp>

    //我的邀请中升级成员

    fun teamInviteUpgrade(
            memId: String,
            page:Int,
            rows:Int
    ): Observable<List<TeamInviteUpgradeSingleResp>?>


    //我的团队

    fun myTeam(
            memId: String,
            page: Int,
            rows: Int
    ):Observable<MyTeamResp>


    //我的团队中升级成员
    fun myTeamUpgrade(
            memId: String,
            page: Int,
            rows: Int
    ):Observable<MyTeamUpgradeResp>

    //我的界面

    //我的访客记录-我的访客
    fun myVisitors(
             memId: String,
             page: Int,
             rows: Int,
             type:String
    ):Observable<MyVisitorsResp>

    //我的访客记录-我的粉丝
    fun myFans(
            memId: String,
            page: Int,
            rows: Int,
            type: String
    ):Observable<MyFansResp>

    //开通店铺

    //我的地址列表

    //查看地址详情

    //删除我的地址

    //新增地址

    //修改地址
}