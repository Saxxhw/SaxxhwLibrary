package com.saxxhw

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.saxxhw.library.base.BaseActivity
import com.saxxhw.library.widget.state.StateLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_main

    override fun getStateLayout(): StateLayout? = stateLayout

    override fun initEventAndData(savedInstanceState: Bundle?) {
        showErrorState()
    }

    override fun setNavigation(mToolBar: Toolbar?) {
        mToolBar?.setNavigationIcon(R.mipmap.ic_menu_personal_center)
        mToolBar?.setNavigationOnClickListener { toast("个人中心") }
    }

    override fun onRetrieve() {
        toast("重新获取数据")
    }
}