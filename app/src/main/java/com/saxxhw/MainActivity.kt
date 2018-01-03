package com.saxxhw

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.View
import com.saxxhw.library.base.BaseActivity
import com.saxxhw.library.util.AtyManagerUtil
import com.saxxhw.library.widget.BottomNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), BottomNavigationBar.MenuItemClickListener {

    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData(savedInstanceState: Bundle?) {
        Handler().postDelayed({ bottomMenu.setXmlResource(R.xml.bottombar_tabs_color_changing2) }, 1000 * 12)
    }

    override fun bindListener() {
        bottomMenu.setOnMenuItemClickListener(this)
    }

    override fun setNavigation(mToolBar: Toolbar?) {
        mToolBar?.setNavigationIcon(R.mipmap.ic_menu_personal_center)
        mToolBar?.setNavigationOnClickListener { toast("个人中心") }
    }

    override fun onItemClick(view: View) {
        when (view.id) {
            R.id.tab_recharge -> toast("充值")
            R.id.tab_repair -> AtyManagerUtil.instance.finishActivity(this)
        }
    }
}