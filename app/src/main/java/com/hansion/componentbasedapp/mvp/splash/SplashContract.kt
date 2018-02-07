package com.hansion.componentbasedapp.mvp.splash

import com.hansion.commonlibrary.mvp.BaseContract

/**
 * Description：
 * Author: Hansion
 * Time: 2018/2/6 10:06
 */
class SplashContract {
    interface ISplashView : BaseContract.IBaseView {

        fun delayGoOtherActivity(seconds: Long)

        fun goOtherActivityNow()
    }

    interface ISplashPresenter : BaseContract.IPresenter {
    }

    interface ISplashModel : BaseContract.IModel {
    }
}