package com.hansion.commonlibrary.mvp

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/25 15:56
 */

open class BasePresenter<V : BaseContract.IBaseView, M : BaseContract.IModel> : BaseContract.IPresenter {

    var mView: V? = null
    var mModel: M? = null

    constructor(mView: V) {
        this.mView = mView
    }

    constructor(mView: V, mModel: M) {
        this.mView = mView
        this.mModel = mModel
    }

    override fun destroy() {
        mModel?.destroy()
        mModel = null
        mView = null
    }
}