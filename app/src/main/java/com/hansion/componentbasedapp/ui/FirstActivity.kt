package com.hansion.componentbasedapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 为了解决冷启动白屏的问题而创建的Activity
 * 如无此需要,可在清单文件中更改启动页为SplashActivity
 * 该Activity无布局文件,仅靠自定义主题FirstActivityTheme中设置的windowBackground属性来显示背景图
 * 更改该属性可更改背景图
 */
class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_first)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}
