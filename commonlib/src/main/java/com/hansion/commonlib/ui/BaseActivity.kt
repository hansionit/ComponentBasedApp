package com.hansion.commonlibrary.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.hansion.commonlib.R
import com.hansion.commonlib.view.slideback.SlideBackAppCompatActivity
import com.hansion.commonlibrary.mvp.BaseContract
import com.hansion.commonlibrary.view.ToolBarConfig
import kotlinx.android.synthetic.main.header_layout.*
import javax.inject.Inject

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/25 15:40
 */
abstract class BaseActivity<P : BaseContract.IPresenter> : SlideBackAppCompatActivity(), BaseContract.IBaseView {

    @Inject
    lateinit var mPresenter:P

    //当前类名，打印使用
    private val mClassName = this.javaClass
    private var headerLayout: RelativeLayout? = null
    companion object {
        //全屏标志
        val SCREEN_FULL = 0

        //不携带数据跳转Activity
        internal fun switchTo(activity: Activity?, targetActivity: Class<out Activity>, needFinish: Boolean) {
            if (activity == null) {
                return
            }
            activity.startActivity(Intent(activity, targetActivity))
            if (needFinish) {
                activity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (setFullScreen() == SCREEN_FULL) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            setContentView(setLayoutRes())
        } else {

            if (isNeedHeadLayout()) {
                val linearLayout = LinearLayout(this)
                linearLayout.orientation = LinearLayout.VERTICAL
                headerLayout = LayoutInflater.from(this).inflate(R.layout.header_layout, null) as RelativeLayout
                linearLayout.addView(headerLayout)
                linearLayout.addView(LayoutInflater.from(this).inflate(setLayoutRes(), null))
                setContentView(linearLayout)
            } else {
//                Sofia.with(this).invasionStatusBar()
                setContentView(setLayoutRes())
            }
        }

        initBaseView()
        initData()
    }

    private fun initBaseView() {
        if(isNeedHeadLayout()) {
            mTitleBarRightIcon.visibility = View.GONE
            mTitleBarRightTxt.visibility = View.GONE
            setSupportActionBar(toolbar)
            supportActionBar?.title = ""
        }

        initView()
    }


    fun setToolBar(toolbarConfig: ToolBarConfig) {
        if (!isNeedHeadLayout()) {
            return
        }

        if (toolbarConfig.bgColor != 0) {
            headerLayout?.setBackgroundColor(toolbarConfig.bgColor)
        }


        if (toolbarConfig.customView != null) {
            toolbar.removeAllViews()
            toolbar.addView(toolbarConfig.customView)
            return
        }

        if (toolbarConfig.logoPath != null) {
            mTitleBarIcon.visibility = View.VISIBLE
            Glide.with(this).load(toolbarConfig.logoPath).into(mTitleBarIcon)
        } else {
            mTitleBarIcon.visibility = View.GONE
        }

        if (toolbarConfig.rightResId != 0) {
            mTitleBarRightTxt.visibility = View.GONE
            mTitleBarRightIcon.visibility = View.VISIBLE
            mTitleBarRightIcon.setImageResource(toolbarConfig.rightResId)
            mTitleBarRightIcon.setOnClickListener(toolbarConfig.rightListener)
        } else if (toolbarConfig.rightText != null) {
            mTitleBarRightTxt.visibility = View.VISIBLE
            mTitleBarRightIcon.visibility = View.GONE
            mTitleBarRightTxt.text = toolbarConfig.rightText
            mTitleBarRightTxt.setOnClickListener(toolbarConfig.rightListener)
        } else {
            mTitleBarRightTxt.visibility = View.GONE
            mTitleBarRightIcon.visibility = View.GONE
        }


        if (toolbarConfig.title != null) {
            mTitleBarTitle.visibility = View.VISIBLE
            mTitleBarTitle.text = toolbarConfig.title
        } else {
            mTitleBarTitle.visibility = View.GONE
        }

        if (toolbarConfig.isNeedBackKey) {
            mTitleBarBackIcon.visibility = View.VISIBLE
            mTitleBarBackIcon.setOnClickListener({ finish() })
        } else {
            mTitleBarBackIcon.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
    }

    //设置是否是全屏，默认：notitlebar
    open fun setFullScreen(): Int = -1
    abstract fun setLayoutRes() : Int
    abstract fun initView()
    abstract fun initData()
    //是否需要标题栏
    abstract fun isNeedHeadLayout(): Boolean

    fun switchTo(activity: Activity?, targetActivity: Class<out Activity>, needFinish: Boolean) {
        if (activity == null) {
            return
        }
        activity.startActivity(Intent(activity, targetActivity))
        if (needFinish) {
            activity.finish()
        }
    }

}