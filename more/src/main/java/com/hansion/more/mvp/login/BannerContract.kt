package com.hansion.componentbasedapp.mvp.login

import com.hansion.commonlibrary.mvp.BaseContract

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:51
 */
interface BannerContract {

    interface IBannerView : BaseContract.IBaseView {
        fun showMyAge(age: Int)
    }

    interface IBannerPresenter : BaseContract.IPresenter {
        fun showAge()
    }

    interface IBannerModel : BaseContract.IModel {
        fun getAge(): Int
    }
}