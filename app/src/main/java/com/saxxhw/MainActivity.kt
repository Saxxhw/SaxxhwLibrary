package com.saxxhw

import android.os.Bundle
import android.widget.ListView
import com.saxxhw.library.base.BaseActivity
import com.saxxhw.library.widget.dropdown.SingleChoiceAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    // 默认筛选标题
    private val headers by lazy { resources.getStringArray(R.array.array_order_filter_title).toList() }
    //    // 故障级别
//    private val levelArray by lazy { resources.getStringArray(R.array.array_order_level).toList() }
    // 发生时间
    private val timeArray by lazy { resources.getStringArray(R.array.array_order_occurrence_time).toList() }
    // 处理状态
    private val stateArray by lazy { resources.getStringArray(R.array.array_order_processing_state).toList() }

    // 筛选控件View集合
    private lateinit var popupViews: List<ListView>

    // 筛选控件数据填充器
//    private lateinit var levelAdapter: DropDownAdapter
    private lateinit var timeAdapter: SingleChoiceAdapter
    private lateinit var stateAdapter: SingleChoiceAdapter

    // 选中状态
    private var level: Int = 0
    private var time: Int = 0
    private var state = -1


    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData(savedInstanceState: Bundle?) {
// 初始化下拉菜单
        initDropDownMenu()
    }

    override fun bindListener() {

    }

    /**
     * 初始化dropDownMenu控件
     */
    private fun initDropDownMenu() {
        //init level menu
//        val levelView = ListView(activity)
//        levelView.dividerHeight = 0
//        levelAdapter = DropDownAdapter(activity)
//        levelAdapter.addItems(levelArray)
//        levelView.adapter = levelAdapter

        // init time menu
        val timeView = ListView(this)
        timeView.dividerHeight = 0
        timeAdapter = SingleChoiceAdapter(this)
        timeAdapter.addItems(timeArray)
        timeView.adapter = timeAdapter

        // init state menu
        val stateView = ListView(this)
        stateView.dividerHeight = 0
        stateAdapter = SingleChoiceAdapter(this)
        stateAdapter.addItems(stateArray)
        stateView.adapter = stateAdapter

        //init popupViews
        popupViews = listOf(timeView, stateView)

//        //add item click event
//        levelView.setOnItemClickListener({ _, _, position, _ ->
//            levelAdapter.setCheckItem(position)
//            dropDownMenu.setTabText(if (position == 0) headers[0] else levelArray[position])
//            dropDownMenu.closeMenu()
//            level = position
//            mPresenter.getOrderList(level, time, state, true)
//        })
        timeView.setOnItemClickListener({ _, _, position, _ ->
            timeAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position == 0) headers[0] else timeArray[position])
            dropDownMenu.closeMenu()
            time = position
        })
        stateView.setOnItemClickListener({ _, _, position, _ ->
            stateAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position == 0) headers[1 ] else stateArray[position])
            dropDownMenu.closeMenu()
            state = position - 1
        })

        //init dropdownview
        dropDownMenu.setDropDownMenu(headers, popupViews)
    }
}