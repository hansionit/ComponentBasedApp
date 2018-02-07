package com.hansion.commonlibrary.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.hansion.commonlib.R
import com.hansion.commonlibrary.mvp.BaseContract
import com.hansion.commonlibrary.view.ToolBarConfig
import kotlinx.android.synthetic.main.header_layout.*

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 15:42
 */
abstract class BaseFragment<P : BaseContract.IPresenter> : Fragment(), BaseContract.IBaseView {

    @JvmField
    var mPresenter: P? = null

    protected var root: View? = null
    private var headerLayout: RelativeLayout? = null
    private var hasInit = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {

            if (isNeedHeadLayout()) {
                val linearLayout = LinearLayout(activity)
                linearLayout.orientation = LinearLayout.VERTICAL
                headerLayout = LayoutInflater.from(activity).inflate(R.layout.header_layout, null) as RelativeLayout
                linearLayout.addView(headerLayout)
                linearLayout.addView(LayoutInflater.from(activity).inflate(setLayoutRes(), null))
                root = linearLayout
            } else {
                root = inflater?.inflate(setLayoutRes(), container, false)
            }

            if (root?.parent != null) {
                (root?.parent as ViewGroup).removeView(root)
            }

            container?.addView(root)
        }

        if (root?.parent != null) {
            (root?.parent as ViewGroup).removeView(root)
        }

        return root
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseView()
        initData()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (root != null && userVisibleHint && !hasInit) {
            hasInit = true
            updateView()
        }
    }

    /**
     * 视图真正可见的时候才调用
     */

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (root != null && isVisibleToUser && !hasInit) {
            hasInit = true
            updateView()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(mPresenter!= null) {
            mPresenter!!.destroy()
        }
    }


    private fun initBaseView() {
        if(isNeedHeadLayout()) {
            mTitleBarRightIcon.visibility = GONE
            mTitleBarRightTxt.visibility = GONE
            (activity as BaseActivity<*>).setSupportActionBar(toolbar)
            (activity as BaseActivity<*>).supportActionBar?.title = ""
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
            mTitleBarIcon.visibility = GONE
        }

        if (toolbarConfig.rightResId != 0) {
            mTitleBarRightTxt.visibility = GONE
            mTitleBarRightIcon.visibility = View.VISIBLE
            mTitleBarRightIcon.setImageResource(toolbarConfig.rightResId)
            mTitleBarRightIcon.setOnClickListener(toolbarConfig.rightListener)
        } else if (toolbarConfig.rightText != null) {
            mTitleBarRightTxt.visibility = View.VISIBLE
            mTitleBarRightIcon.visibility = GONE
            mTitleBarRightTxt.text = toolbarConfig.rightText
            mTitleBarRightTxt.setOnClickListener(toolbarConfig.rightListener)
        } else {
            mTitleBarRightTxt.visibility = GONE
            mTitleBarRightIcon.visibility = GONE
        }


        if (toolbarConfig.title != null) {
            mTitleBarTitle.visibility = View.VISIBLE
            mTitleBarTitle.text = toolbarConfig.title
        } else {
            mTitleBarTitle.visibility = GONE
        }

        if (toolbarConfig.isNeedBackKey) {
            mTitleBarBackIcon.visibility = View.VISIBLE
            mTitleBarBackIcon.setOnClickListener({ activity.finish() })
        } else {
            mTitleBarBackIcon.visibility = GONE
        }
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun setLayoutRes(): Int

    //是否需要标题栏
    abstract fun isNeedHeadLayout(): Boolean

    abstract fun updateView()

}