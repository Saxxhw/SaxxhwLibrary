package com.saxxhw.library.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.saxxhw.library.R

/**
 * Created by Saxxhw on 2017/12/6.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */

class ChatExtendMenu @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    companion object {
        private val SPAN = 3
    }

    private var adapter: MenuAdapter = MenuAdapter()
    private var mList: List<ExtendItem>? = null

    init {
        this.setAdapter(adapter)
        this.layoutManager = GridLayoutManager(context, SPAN)
    }

    /**
     * 填充数据
     */
    fun addData(mList: List<ExtendItem>) {
        this.mList = mList
        adapter.setNewData(mList)
    }

    /**
     * 绑定监听器
     */
    fun setOnMenuItemClickListener(clickListener: BaseQuickAdapter.OnItemClickListener) {
        adapter.onItemClickListener = clickListener
    }
}

class MenuAdapter : BaseQuickAdapter<ExtendItem, BaseViewHolder>(R.layout.widget_item_chat_extend_menu, null) {

    override fun convert(helper: BaseViewHolder?, item: ExtendItem?) {
        val tvItem = helper?.getView<TextView>(R.id.tv_item)
        if (item != null) {
            tvItem?.setText(item.name)
            tvItem?.setCompoundDrawablesWithIntrinsicBounds(0, item.image, 0, 0)
        }
    }
}

data class ExtendItem(val image: Int, val name: Int, val clickTag: Int)