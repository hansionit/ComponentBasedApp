package com.hansion.more.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hansion.commonlib.ARouterList.MORE_MODULE_TESTFRAGMENT
import com.hansion.commonlib.utils.LogUtil
import com.hansion.commonlib.utils.MyToastUtils
import com.hansion.commonlibrary.ui.BaseFragment
import com.hansion.commonlibrary.view.ToolBarConfig
import com.hansion.componentbasedapp.mvp.login.BannerContract
import com.hansion.componentbasedapp.mvp.login.BannerPresenter
import com.hansion.more.R
import kotlinx.android.synthetic.main.fragment_test.*


/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 16:26
 */
@Route(path = MORE_MODULE_TESTFRAGMENT)
class TestFragment : BaseFragment<BannerPresenter>(), BannerContract.IBannerView , View.OnClickListener{

    override fun onClick(view: View?) {
       when(view?.id) {
           R.id.mTitleBarRightIcon -> {
               MyToastUtils.showShortToast("点击右侧icon")
           }
       }
    }


    override fun showMyAge(age: Int) {

    }

    override fun initView() {

    }

    override fun initData() {
        mText.text = "TestFragment"+Math.random()
    }

    override fun updateView() {
        LogUtil.e("真正可见")
        val toolbarConfig = ToolBarConfig()
        toolbarConfig.title = "真正可见"
        toolbarConfig.rightResId = R.mipmap.ic_launcher
        toolbarConfig.rightListener = this
        setToolBar(toolbarConfig)
    }

    override fun setLayoutRes(): Int = R.layout.fragment_test

    override fun isNeedHeadLayout(): Boolean = true


}