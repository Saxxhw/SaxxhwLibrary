package com.saxxhw.library.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaopiz.kprogresshud.KProgressHUD
import com.saxxhw.library.widget.state.StateLayout
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

/**
 * Created by Saxxhw on 2017/4/11.
 * 邮箱：Saxxhw@126.com
 * 功能：Fragment基类(无MVP)
 */

abstract class BaseFragment : Fragment() {

    private lateinit var mView: View

    private var mStateLayout: StateLayout? = null

    private var mProgressDialog: KProgressHUD? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(getLayout(), null)
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStateLayout = getStateLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEventAndData()
        bindListener()
    }

    override fun onDetach() {
        super.onDetach()
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(this, null)
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    /**
     * 展示进度条
     */
    protected fun showProgressDialog() {
        mProgressDialog = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        mProgressDialog?.setCancellable(false)
        mProgressDialog?.show()
    }

    /**
     * 隐藏进度条
     */
    protected fun hideProgressDialog() {
        if (null != mProgressDialog && mProgressDialog?.isShowing!! && !activity!!.isFinishing) {
            mProgressDialog?.dismiss()
        }
    }

    /**
     * 展示加载中状态
     */
    protected fun showLoadingState() {
        if (null == mStateLayout) {
            throw IllegalArgumentException("You must return a right target view for loading")
        }
        mStateLayout?.showLoading()
    }

    /**
     * 展示界面内容
     */
    protected fun showContentState() {
        if (null == mStateLayout) {
            throw IllegalArgumentException("You must return a right target view for loading")
        }
        mStateLayout?.showContent()
    }

    /**
     * 展示空数据状态
     */
    protected fun showEmptyState() {
        if (null == mStateLayout) {
            throw IllegalArgumentException("You must return a right target view for loading")
        }
        mStateLayout?.showEmpty(null, null, View.OnClickListener { })
    }

    /**
     * 展示请求错误状态
     */
    protected fun showErrorState() {
        if (null == mStateLayout) {
            throw IllegalArgumentException("You must return a right target view for loading")
        }
        mStateLayout?.showError("", "")
    }

    protected abstract fun getLayout(): Int

    open fun getStateLayout(): StateLayout? = null

    protected abstract fun initEventAndData()

    open fun bindListener() {}
}
