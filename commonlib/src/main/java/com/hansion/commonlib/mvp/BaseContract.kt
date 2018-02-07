package com.hansion.commonlibrary.mvp


/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/25 15:51
 */
interface BaseContract {

    interface IPresenter {

        fun destroy()
    }


    interface IBaseView {
    }

    interface IModel {

        fun destroy()
    }
}