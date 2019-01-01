package com.zejyej.template.service.impl

import com.zejyej.base.extension.convert
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.data.protocol.resp.*
import com.zejyej.template.data.repository.UserRepositry
import com.zejyej.template.service.UserService
import io.reactivex.Observable


import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class UserServiceImpl @Inject constructor(): UserService{
    override fun resetPassword(req: ResetPasswordReq): Observable<Boolean> {
        return userRepositry.resetPassword(req).convert()
    }

    override fun verifyCode(req: VerifyCodeReq): Observable<Boolean> {
        return userRepositry.verifyCode(
                req
        ).convert()
    }

    override fun myVisitors(memId: String, page: Int, rows: Int, type: String): Observable<MyVisitorsResp> {
        return userRepositry.myVisitors(
                memId,
                page,
                rows,
                type
        ).convert()
    }

    override fun myFans(memId: String, page: Int, rows: Int, type: String): Observable<MyFansResp> {
        return userRepositry.myFans(
                memId,
                page,
                rows,
                type
        ).convert()
    }

    override fun myTeam(memId: String, page: Int, rows: Int): Observable<MyTeamResp> {
        return userRepositry.myTeam(
                memId,
                page,
                rows
        ).convert()
    }

    override fun myTeamUpgrade(memId: String, page: Int, rows: Int): Observable<MyTeamUpgradeResp> {
        return userRepositry.myTeamUpgrade(
                memId,
                page,
                rows
        ).convert()
    }

    override fun teamInviteUpgrade(memId: String, page: Int, rows: Int): Observable<List<TeamInviteUpgradeSingleResp>?> {
        return userRepositry.teamInviteUpgrade(
                memId,
                page,
                rows
        ).convert()
    }

    override fun teamInvite(memId: String, page: Int, rows: Int, type: String): Observable<TeamInviteResp> {
        return userRepositry.teamInvite(
                memId,
                page,
                rows,
                type
        ).convert()
    }

    override fun myInvite(memId: String, page: Int, rows: Int, type: String): Observable<MyInviteResp> {
        return userRepositry.myInvite(
                memId,
                page,
                rows,
                type
        ).convert()
    }

    @Inject
    lateinit var userRepositry: UserRepositry

    override fun register(req: RegisterReq): Observable<RegisterResp> {
        return userRepositry.register(req).convert()
    }

    override fun login(req: LoginReq): Observable<LoginResp> {
        return userRepositry.login(req).convert()
    }




}