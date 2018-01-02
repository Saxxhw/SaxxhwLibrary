package com.saxxhw

import android.app.Application
import com.hyphenate.easeui.init.EaseInitHelper

/**
 * Created by Saxxhw on 2018/1/2.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        EaseInitHelper.getInstance().init(this, MainActivity::class.java, MainActivity::class.java)
    }
}