package com.zejyej.template.injection.module

import com.zejyej.template.service.UserService
import com.zejyej.template.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
@Module
class UserModule {

    @Provides
    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }
}