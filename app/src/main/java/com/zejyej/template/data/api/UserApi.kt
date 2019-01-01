package com.zejyej.template.data.api

import com.zejyej.base.data.protocol.resp.BaseResponse
import com.zejyej.template.data.protocol.req.LoginReq
import com.zejyej.template.data.protocol.req.RegisterReq
import com.zejyej.template.data.protocol.req.ResetPasswordReq
import com.zejyej.template.data.protocol.req.VerifyCodeReq
import com.zejyej.template.data.protocol.resp.*
import io.reactivex.Observable
import retrofit2.http.*


/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
interface UserApi {

    //注册

    @POST("member/api/register")
    fun register(@Body req:RegisterReq): Observable<BaseResponse<RegisterResp>>

    //登陆
    @POST("member/api/login")
    fun login(@Body req:LoginReq): Observable<BaseResponse<LoginResp>>

    //获取验证码
    @POST("member/api/verifyCode")
    fun verifyCode(@Body req: VerifyCodeReq): Observable<BaseResponse<Boolean>>

    //修改密码
    @PUT("member/api/resetPassword")
    fun resetPassword(@Body req: ResetPasswordReq): Observable<BaseResponse<Boolean>>

    //我的邀请
    @GET("member/api/inviteInfo/{memId}/{page}/{rows}")
    fun myInvite(
            @Path("memId") memId: String,
            @Path("page") page:Int,
            @Path("rows") rows:Int,
            @Query("type") type:String
    ): Observable<BaseResponse<MyInviteResp>>

    @GET("member/api/inviteInfo/{memId}/{page}/{rows}")
    fun teamInvite(
            @Path("memId") memId: String,
            @Path("page") page:Int,
            @Path("rows") rows:Int,
            @Query("type") type:String
    ): Observable<BaseResponse<TeamInviteResp>>

    //我的邀请中升级成员
    @GET("member/api/upGradeInviteInfo/{memId}/{page}/{rows}")
    fun teamInviteUpgrade(
            @Path("memId") memId: String,
            @Path("page") page:Int,
            @Path("rows") rows:Int
    ): Observable<BaseResponse<List<TeamInviteUpgradeSingleResp>?>>

    //我的团队
    @GET("member/api/teamInfo/{memId}/{page}/{rows}")
    fun myTeam(
            @Path("memId") memId: String,
            @Path("page") page: Int,
            @Path("rows") rows: Int
    ):Observable<BaseResponse<MyTeamResp>>

    //我的团队中升级成员
    @GET("member/api/upGradeTeam/{memId}/{page}/{rows}")
    fun myTeamUpgrade(
            @Path("memId") memId: String,
            @Path("page") page: Int,
            @Path("rows") rows: Int
    ):Observable<BaseResponse<MyTeamUpgradeResp>>
    //我的界面
    @GET("member/api/personalInfo/{memId}")
    fun mine(@Path("memId") memId:String)

    //我的访客记录-我的访客
    @GET("member/api/shopHistory/{memId}/{page}/{rows}")
    fun myVisitors(
            @Path("memId") memId: String,
            @Path("page") page: Int,
            @Path("rows") rows: Int,
            @Query("type") type:String
    ):Observable<BaseResponse<MyVisitorsResp>>
    //我的访客记录-我的粉丝
    @GET("member/api/shopHistory/{memId}/{page}/{rows}")
    fun myFans(
            @Path("memId") memId: String,
            @Path("page") page: Int,
            @Path("rows") rows: Int,
            @Query("type") type:String
    ):Observable<BaseResponse<MyFansResp>>


    //开通店铺

    //我的地址列表

    //查看地址详情

    //删除我的地址

    //新增地址

    //修改地址
}