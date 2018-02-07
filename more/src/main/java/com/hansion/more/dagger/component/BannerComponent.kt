package com.hansion.componentbasedapp.dagger.component

import android.app.Activity
import android.support.v4.app.Fragment
import com.hansion.componentbasedapp.dagger.module.BannerModule
import com.hansion.more.ui.TestFragment
import dagger.Component

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 14:23
 */
@Component(modules = arrayOf(BannerModule::class))
interface BannerComponent {
     fun inject(activity: TestFragment)
}