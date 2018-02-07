package com.hansion.componentbasedapp.dagger.module

import com.hansion.componentbasedapp.mvp.login.SplashModle
import com.hansion.componentbasedapp.mvp.splash.SplashContract
import dagger.Module
import dagger.Provides

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:23
 */
@Module
class SplashModule(private val view: SplashContract.ISplashView) {

    @Provides
    internal fun provideSplashView(): SplashContract.ISplashView = this.view


    @Provides
    internal fun provideSplashModle(model: SplashModle): SplashContract.ISplashModel = model
}