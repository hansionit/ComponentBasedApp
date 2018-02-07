package com.hansion.componentbasedapp.mvp.login

import com.hansion.commonlibrary.mvp.BasePresenter
import javax.inject.Inject

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/26 15:06
 */
class BannerPresenter @Inject
constructor(mView: BannerContract.IBannerView,mModel: BannerContract.IBannerModel) :
        BasePresenter<BannerContract.IBannerView, BannerContract.IBannerModel>(mView,mModel),
        BannerContract.IBannerPresenter {


    override fun showAge() {
        val age = mModel?.getAge()
        mView?.showMyAge(age!!)
    }
}