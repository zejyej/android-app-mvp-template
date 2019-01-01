package com.zejyej.template.injection.component

import com.zejyej.base.injection.component.ActivityComponent
import com.zejyej.base.injection.scope.PerComponentScope
import com.zejyej.template.injection.module.UserModule
import com.zejyej.template.ui.activity.impl.*
import com.zejyej.template.ui.fragment.impl.MyFansFragment
import com.zejyej.template.ui.fragment.impl.MyInviteFragment
import com.zejyej.template.ui.fragment.impl.MyVisitorsFragment
import com.zejyej.template.ui.fragment.impl.TeamInviteFragment
import dagger.Component

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [UserModule::class])
interface UserComponent {
    fun inject(activity: SplashActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: MyTeamActivity)
    fun inject(activity: ResetPasswordActivity)
    fun inject(activity: VipActivity)
    fun inject(activity: MineActivity)
    fun inject(activity: AddReceiveInfoActivity)

    fun inject(fragment: MyInviteFragment)
    fun inject(fragment: TeamInviteFragment)
    fun inject(fragment: MyVisitorsFragment)
    fun inject(fragment: MyFansFragment)

}