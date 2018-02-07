package com.hansion.componentbasedapp.ui

import android.content.Intent
import android.os.Handler
import android.os.Message
import com.hansion.commonlibrary.ui.BaseActivity
import com.hansion.componentbasedapp.R
import com.hansion.componentbasedapp.dagger.component.DaggerSplashComponent
import com.hansion.componentbasedapp.dagger.module.SplashModule
import com.hansion.componentbasedapp.mvp.login.SplashPresenter
import com.hansion.componentbasedapp.mvp.splash.SplashContract
import java.lang.ref.WeakReference


class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.ISplashView {


    private val mHandler = MyHandler(this)

    private class MyHandler(activity: SplashActivity) : Handler() {
        private val mActivity: WeakReference<SplashActivity> = WeakReference(activity)
        override fun handleMessage(msg: Message) {
            val splashActivity = mActivity.get()
            if (splashActivity != null) {
                when (msg.what) {
                    0 -> {  //跳转至其他Activity
                        splashActivity.goOtherActivityNow()
                    }
                }
            }
        }
    }

    override fun setFullScreen(): Int = SCREEN_FULL

    override fun setLayoutRes() = R.layout.activity_splash

    override fun initView() {
        DaggerSplashComponent.builder().splashModule(SplashModule(this)).build().inject(this)
    }

    override fun initData() {
        delayGoOtherActivity(2000)
    }

    override fun isNeedHeadLayout() = false

    override fun delayGoOtherActivity(seconds: Long) {
        mHandler.sendEmptyMessageDelayed(0, seconds)
    }

    override fun goOtherActivityNow() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

}
