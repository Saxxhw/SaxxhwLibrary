package com.saxxhw.library.widget

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.support.annotation.IntRange
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import com.saxxhw.library.R
import kotlinx.android.synthetic.main.widget_bottom_navigation_bar.view.*
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

/**
 * Created by Saxxhw on 2017/12/30.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
class BottomNavigationBar : LinearLayout {

    companion object {
        private val DEFAULT_XML_RESOURCE = 0

        private val TAB_TAG = "tab"
        private val RESOURCE_NOT_FOUND = 0

        private val ID = "id"
        private val ICON = "icon"
        private val TITLE = "title"
    }

    // 上下文对象
    private var mContext: Context

    // 全部控件集合
    private lateinit var viewArray: Array<View>

    // 配置文件资源id
    private var tabXmlResource: Int

    // xml解析器
    private lateinit var parser: XmlResourceParser

    // 点击事件
    private var menuItemClickListener: MenuItemClickListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, DEFAULT_XML_RESOURCE)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        // 绑定布局
        LayoutInflater.from(context).inflate(R.layout.widget_bottom_navigation_bar, this, true)
        // 设置布局对齐方向
        gravity = Gravity.BOTTOM
        // 初始化
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar, defStyleAttr, 0)

        // 其他按钮
        tabXmlResource = ta.getResourceId(R.styleable.BottomNavigationBar_bb_tabXmlResource, 0)
        setXmlResource(tabXmlResource)

        // 绑定监听器
        bindListener()
    }

    /**
     * 绑定监听事件
     */
    private fun bindListener() {
        val cbArray = arrayOf(cb1, cb2, cb3, cb4)
        arrayOf(rlyt1, rlyt2, rlyt3, rlyt4)
                .forEachIndexed { index, relativeLayout ->
                    relativeLayout.setOnClickListener {
                        val cb = cbArray[index]
                        if (cb.setChecked()) {
                            menuItemClickListener?.onItemClick(cb)
                        }
                    }
                }
        ivCenter.setOnClickListener { menuItemClickListener?.onItemClick(ivCenter) }
        rlyt1.performClick()
    }

    /**
     * 解析xml文件
     */
    private fun parseTabs() {
        viewArray = arrayOf(cb1, cb2, ivCenter, cb3, cb4)
        try {
            var index = 0
            var eventType: Int
            do {
                eventType = parser.next()
                if (eventType == XmlResourceParser.START_TAG && TAB_TAG == parser.name) {
                    println(eventType)
                    parseNewTab(parser, viewArray[index++])
                }
            } while (eventType != XmlResourceParser.END_DOCUMENT)
        } catch (e: IOException) {
            e.printStackTrace()
            throw TabParserException()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
            throw TabParserException()
        }
    }

    /**
     * 将解析出的数据赋值给控件
     */
    private fun parseNewTab(parser: XmlResourceParser, view: View) {
        val numberOfAttributes = parser.attributeCount
        for (i in 0 until numberOfAttributes) {
            val attrName = parser.getAttributeName(i)
            when (attrName) {
                ID -> {
                    val id = parser.getIdAttributeResourceValue(i)
                    view.id = id
                }
                ICON -> {
                    val icon = parser.getAttributeResourceValue(i, RESOURCE_NOT_FOUND)
                    if (view is CheckBox) {
                        val drawable = ContextCompat.getDrawable(mContext, icon)
                        view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                    } else if (view is ImageView) {
                        view.setImageResource(icon)
                    }
                }
                TITLE -> {
                    if (view is CheckBox) {
                        val title = getTitleValue(parser, i)
                        view.text = title
                    }
                }
            }
        }
    }

    /**
     * 获取文本
     */
    private fun getTitleValue(parser: XmlResourceParser, @IntRange(from = 0) attrIndex: Int): String {
        val titleResource = parser.getAttributeResourceValue(attrIndex, 0)
        return if (titleResource == RESOURCE_NOT_FOUND) parser.getAttributeValue(attrIndex) else context.getString(titleResource)
    }

    /**
     * 设置CheckBox选中
     */
    private fun CheckBox.setChecked(): Boolean {
        // 判断是否已选中
        if (isChecked) {
            return false
        }
        clearChecked()
        isChecked = true
        return true
    }

    /**
     * 清空选中标识
     */
    private fun clearChecked() {
        arrayOf(cb1, cb2, cb3, cb4).filter { it.isChecked }.forEach { it.isChecked = false }
    }

    /**
     * 监控事件
     */
    interface MenuItemClickListener {
        fun onItemClick(view: View)
    }

    class TabParserException : RuntimeException()

    /* ################################ getter&setter ################################ */

    /**
     * 绑定监听器
     */
    fun setOnMenuItemClickListener(mListener: MenuItemClickListener) {
        this.menuItemClickListener = mListener
    }

    /**
     * 设置xml资源id
     */
    fun setXmlResource(resId: Int) {
        if (DEFAULT_XML_RESOURCE != resId) {
            this.tabXmlResource = resId
            this.parser = context.resources.getXml(tabXmlResource)
            parseTabs()
        }
    }
}