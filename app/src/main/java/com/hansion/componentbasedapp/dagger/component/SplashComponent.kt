package com.hansion.componentbasedapp.dagger.component

import com.hansion.componentbasedapp.ui.SplashActivity
import com.hansion.componentbasedapp.dagger.module.SplashModule
import com.hansion.componentbasedapp.ui.MainActivity
import dagger.Component

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:23
 */
@Component(modules = arrayOf(SplashModule::class))
interface SplashComponent {
     fun inject(activity: SplashActivity)
     fun inject(activity: MainActivity)
}