package com.hansion.componentbasedapp.dagger.module

import com.hansion.componentbasedapp.mvp.login.BannerContract
import com.hansion.componentbasedapp.mvp.login.BannerModle
import dagger.Module
import dagger.Provides

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:23
 */
@Module
class BannerModule(private val view: BannerContract.IBannerView) {

    @Provides
    internal fun provideBannerView(): BannerContract.IBannerView = this.view


    @Provides
    internal fun provideBannerModle(model: BannerModle): BannerContract.IBannerModel = model
}