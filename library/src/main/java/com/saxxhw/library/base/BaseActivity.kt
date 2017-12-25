package com.saxxhw.library.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.kaopiz.kprogresshud.KProgressHUD

import com.saxxhw.library.R
import com.saxxhw.library.util.AtyManagerUtil
import com.saxxhw.library.widget.state.StateLayout
import java.lang.IllegalArgumentException

/**
 * Created by Saxxhw on 2017/4/11.
 * 邮箱：Saxxhw@126.com
 * 功能：Activity基类(无MVP)
 */

abstract class BaseActivity : AppCompatActivity() {

    // 标题栏
    private var mToolBar: Toolbar? = null
    // 状态布局
    private var mStateLayout: StateLayout? = null
    // 加载对话框
    private var mProgressDialog: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initToolBar()
        AtyManagerUtil.instance.addActivity(this)
        val extras = intent.extras
        if (null != extras) {
            getBundleExtras(extras)
        }
        initEventAndData(savedInstanceState)
        bindListener()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        mStateLayout = getStateLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        AtyManagerUtil.instance.removeActivity(this)
    }

    /**
     * 初始化标题栏控件
     */
    open fun initToolBar() {
        mToolBar = findViewById(R.id.toolbar)
        if (null != mToolBar) {
            setSupportActionBar(mToolBar)
            if (!hideBackButton()) {
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                mToolBar?.setNavigationOnClickListener { onBackPressed() }
            }
        }
    }

    /**
     * 展示进度条
     */
    protected fun showProgressDialog() {
        mProgressDialog = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        mProgressDialog?.setCancellable(false)
        mProgressDialog?.show()
    }

    /**
     * 隐藏进度条
     */
    protected fun hideProgressDialog() {
        if (null != mProgressDialog && mProgressDialog!!.isShowing && !isFinishing) {
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

    protected abstract fun initEventAndData(savedInstanceState: Bundle?)

    open fun getBundleExtras(extras: Bundle) {}

    open fun bindListener() {}

    open fun hideBackButton(): Boolean = false
}