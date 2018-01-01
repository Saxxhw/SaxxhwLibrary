package com.saxxhw.library.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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

    /**
     * 封装请求参数
     *
     * @param map
     * @param list
     * @return
     */
    protected fun getMultipartBody(map: Map<String, String>?, list: List<File>?): MultipartBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        // 遍历key
        if (null != map) {
            for ((key, value) in map) {
                builder.addFormDataPart(key, value)
            }
        }
        // 遍历文件
        if (list != null && !list.isEmpty()) {
            list.indices
                    .map { list[it] }
                    .forEach { builder.addFormDataPart(it.name, it.name, RequestBody.create(MediaType.parse("image/*"), it)) }
        }
        return builder.build()
    }
}