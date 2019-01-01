package com.zejyej.template.data.repository

import com.zejyej.base.data.net.RetrofitFactory
import com.zejyej.base.data.protocol.resp.BaseResponse
import com.zejyej.template.data.api.UserApi
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.data.protocol.resp.*
import io.reactivex.Observable


import javax.inject.Inject

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class UserRepositry @Inject constructor():UserApi{


    override fun myVisitors(memId: String, page: Int, rows: Int, type: String): Observable<BaseResponse<MyVisitorsResp>> {
        return userApi.myVisitors(memId,page,rows,type)
    }

    override fun myFans(memId: String, page: Int, rows: Int, type: String): Observable<BaseResponse<MyFansResp>> {
        return userApi.myFans(memId,page,rows,type)
    }

    override fun myTeam(memId: String, page: Int, rows: Int): Observable<BaseResponse<MyTeamResp>> {
        return userApi.myTeam(memId,page,rows)
    }

    override fun myTeamUpgrade(memId: String, page: Int, rows: Int): Observable<BaseResponse<MyTeamUpgradeResp>> {
        return userApi.myTeamUpgrade(memId,page,rows)
    }

    override fun teamInviteUpgrade(memId: String, page: Int, rows: Int): Observable<BaseResponse<List<TeamInviteUpgradeSingleResp>?>> {
        return userApi.teamInviteUpgrade(memId,page,rows)
    }

    override fun teamInvite(memId: String, page: Int, rows: Int, type: String): Observable<BaseResponse<TeamInviteResp>> {
        return userApi.teamInvite(memId,page,rows,type)
    }

    override fun myInvite(memId: String, page: Int, rows: Int, type: String): Observable<BaseResponse<MyInviteResp>> {
        return userApi.myInvite(memId,page,rows,type)
    }

    override fun mine(memId: String) {

    }


    override fun resetPassword(req: ResetPasswordReq): Observable<BaseResponse<Boolean>> {
        return userApi.resetPassword(req)
    }

    override fun verifyCode(req: VerifyCodeReq): Observable<BaseResponse<Boolean>> {
        return userApi.verifyCode(req)
    }



    override fun register(req: RegisterReq): Observable<BaseResponse<RegisterResp>> {
        return userApi.register(req)
    }

    override fun login(req: LoginReq): Observable<BaseResponse<LoginResp>> {
        return userApi.login(req)
    }

    private val userApi = RetrofitFactory.retrofitInstance.create(UserApi::class.java)

}