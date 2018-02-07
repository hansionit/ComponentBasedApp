package com.hansion.componentbasedapp.mvp.login

import com.hansion.commonlibrary.mvp.BasePresenter
import com.hansion.componentbasedapp.mvp.splash.SplashContract
import javax.inject.Inject

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/26 15:06
 */
class SplashPresenter @Inject
constructor(mView: SplashContract.ISplashView, mModel: SplashContract.ISplashModel) :
        BasePresenter<SplashContract.ISplashView, SplashContract.ISplashModel>(mView,mModel),
        SplashContract.ISplashPresenter {
}