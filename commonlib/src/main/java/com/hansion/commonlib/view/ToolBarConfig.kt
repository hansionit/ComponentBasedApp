package com.hansion.commonlibrary.view

import android.view.View

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/30 17:54
 */
class ToolBarConfig {

    //标题
     var title: String? = null

    //背景颜色
     var bgColor: Int = 0

     var logoPath: String? = null

     var rightResId = 0

     var customView: View? = null


     var rightText: String? = null

     var rightListener: View.OnClickListener? = null

     var isNeedBackKey = true



}