package com.hansion.componentbasedapp.ui

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.hansion.commonlibrary.ui.BaseActivity
import com.hansion.componentbasedapp.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import com.alibaba.android.arouter.launcher.ARouter
import com.hansion.commonlib.ARouterList.MORE_MODULE_TESTFRAGMENT
import com.hansion.componentbasedapp.dagger.component.DaggerSplashComponent
import com.hansion.componentbasedapp.dagger.module.SplashModule
import com.hansion.componentbasedapp.mvp.login.SplashPresenter
import com.hansion.componentbasedapp.mvp.splash.SplashContract


class MainActivity  : BaseActivity<SplashPresenter>(), BottomNavigationView.OnNavigationItemSelectedListener,SplashContract.ISplashView {

    override fun delayGoOtherActivity(seconds: Long) {
    }

    override fun goOtherActivityNow() {
    }


    private var previousPosition: Int = -1
    //记录当前Activity显示的fragment
    private var mContent: Fragment? = null

    private val deviceFragment = ARouter.getInstance().build(MORE_MODULE_TESTFRAGMENT).navigation() as Fragment
    private val galleryFragment = ARouter.getInstance().build(MORE_MODULE_TESTFRAGMENT).navigation() as Fragment
    private val settingFragment = ARouter.getInstance().build(MORE_MODULE_TESTFRAGMENT).navigation() as Fragment
    private val settingFragment1 = ARouter.getInstance().build(MORE_MODULE_TESTFRAGMENT).navigation() as Fragment


    override fun setLayoutRes() = R.layout.activity_main

    override fun initView() {

        mBottomNavigation.onNavigationItemSelectedListener = this

        switchContentKeep(mContent, deviceFragment)
        previousPosition = 0


        mBottomNavigation.itemIconTintList = null
        mBottomNavigation.enableShiftingMode(false)
        mBottomNavigation.enableAnimation(false)
        mBottomNavigation.enableItemShiftingMode(false)
    }

    override fun initData() {
        DaggerSplashComponent.builder().splashModule(SplashModule(this)).build().inject(this)
    }

    override fun isNeedHeadLayout() = false

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var position = 0
        when (item.itemId) {
            R.id.i_our -> {
                position = 0
            }
            R.id.i_timeline -> {
                position = 1
            }
            R.id.i_found -> {
                position = 2
            }
            R.id.i_me -> {
                position = 3
            }
        }

        if (position != previousPosition) {
            when (position) {
                0 -> {
                    switchContentKeep(mContent, deviceFragment)
                }
                1 -> {
                    switchContentKeep(mContent, galleryFragment)
                }
                2 -> {
                    switchContentKeep(mContent, settingFragment)
                }
                3 -> {
                    switchContentKeep(mContent, settingFragment1)
                }
            }
            previousPosition = position
        }

        return true
    }

    private fun switchContentKeep(from: Fragment?, to: Fragment) {
        try {
            if(from == null) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frame_content, to)
                ft.commit()
                to.onResume()
                mContent = to
            } else if (from !== to) {
                val transaction = supportFragmentManager.beginTransaction()
                mContent = to

                // 先判断是否被add过
                if (!to.isAdded) {
                    // 隐藏当前的fragment，add下一个fragment到Activity中
                    transaction.hide(from).add(R.id.frame_content, to).commitAllowingStateLoss()
                } else {
                    // 隐藏当前的fragment，显示下一个fragment
                    transaction.hide(from).show(to).commitAllowingStateLoss()
                    //该命令可注释，若希望fragment切换的过程中，被显示的fragment执行onResume方法，则解注；
                    to.onResume()
                }
            }
        } catch (ignore: Exception) {
        }
    }
}