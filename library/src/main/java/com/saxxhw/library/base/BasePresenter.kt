package com.saxxhw.library.base

/**
 * Created by Saxxhw on 2016/11/26.
 * 邮箱：Saxxhw@126.com
 * 功能：Presenter基类
 */

interface BasePresenter<in T : BaseView> {

    fun attachView(view: T)

    fun detachView()
}
