package com.hansion.commonlib

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/24 11:19
 */
open class BaseApplication : Application() {

    companion object {
        //Context
        private var mInstance: BaseApplication? = null
        var isDebug = true
        //For get Global Context
        val appContext: Context
            get() {
                return if (mInstance != null) {
                    mInstance as Application
                } else {
                    mInstance = BaseApplication()
                    mInstance!!.onCreate()
                    mInstance as Application
                }
            }
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        //判断是否是Debug模式
        Companion.isDebug = isApkDebugable

        if (Companion.isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)     // 尽可能早，推荐在Application中初始化

    }

    //判断是否是Debug模式
    private val isApkDebugable: Boolean
        get() {
            val info = applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }
}