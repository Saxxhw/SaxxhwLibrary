package com.saxxhw.library.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Saxxhw on 2016/8/2.
 * 邮箱：Saxxhw@126.com
 * 功能：基于Rx的Presenter封装,控制订阅的生命周期
 */
open class RxPresenter<T : BaseView> : BasePresenter<T> {

    protected var mView: T? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    private fun unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
        }
    }

    protected fun addSubscribe(subscription: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
        unSubscribe()
    }
}