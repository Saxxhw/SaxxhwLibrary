package com.saxxhw.library.widget.state

import android.view.View

/**
 * Created by Saxxhw on 2017/12/25.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
interface StateLayout {

    fun showContent()

    fun showLoading()

    fun showEmpty(emptyTextTitle: String?, emptyTextContent: String?, onEmptyClickListener: View.OnClickListener)

    fun showError(errorTextTitle: String, errorTextContent: String)
}