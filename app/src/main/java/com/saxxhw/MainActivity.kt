package com.saxxhw

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.View
import com.saxxhw.library.base.BaseActivity
import com.saxxhw.library.util.AtyManagerUtil
import com.saxxhw.library.widget.BottomNavigationBar
import com.saxxhw.library.widget.PartsEntity
import com.saxxhw.library.widget.PartsSelectView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), PartsSelectView.ItemClickListener {


    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData(savedInstanceState: Bundle?) {
        parts.setImageUrl(R.mipmap.test_repair)
        parts.addParts(listOf(
                PartsEntity("1", "部件1", false),
                PartsEntity("2", "部件2", false),
                PartsEntity("3", "部件3", false),
                PartsEntity("4", "部件4", false)
        ))
    }

    override fun bindListener() {
        parts.setOnItemClickListener(this)
    }

    override fun onItemClick(position: Int) {
        println(position)
    }
}