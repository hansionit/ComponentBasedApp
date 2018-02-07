package com.hansion.componentbasedapp.mvp.login

import javax.inject.Inject

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:50
 */
class BannerModle
@Inject constructor() : BannerContract.IBannerModel {

    override fun getAge(): Int = 13


    override fun destroy() {
    }
}