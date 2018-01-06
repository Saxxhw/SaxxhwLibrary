package com.saxxhw.library.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.saxxhw.library.R
import com.saxxhw.library.glide.GlideApp
import kotlinx.android.synthetic.main.widget_parts_select.view.*
import org.jetbrains.anko.dip

/**
 * Created by Saxxhw on 2018/1/6.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
class PartsSelectView : LinearLayout, BaseQuickAdapter.OnItemClickListener {

    // 上下文对象
    private var mContext: Context

    // 数据适配器
    private lateinit var leftAdapter: PartsListAdapter
    private lateinit var rightAdapter: PartsListAdapter

    // 点击事件
    private var listener: ItemClickListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        // 绑定布局
        LayoutInflater.from(context).inflate(R.layout.widget_parts_select, this, true)
        init(context)
        bindListener()
    }

    /**
     * 初始化
     */
    private fun init(context: Context) {
        // 绑定左侧列表适配器
        leftAdapter = PartsListAdapter()
        partsLeft.adapter = leftAdapter
        partsLeft.layoutManager = LinearLayoutManager(context)
        // 绑定右侧列表适配器
        rightAdapter = PartsListAdapter()
        partsRight.adapter = rightAdapter
        partsRight.layoutManager = LinearLayoutManager(context)
    }

    /**
     * 绑定监听器
     */
    private fun bindListener() {
        leftAdapter.onItemClickListener = this
        rightAdapter.onItemClickListener = this
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val leftList = leftAdapter.data
        val rightList = rightAdapter.data
        leftList.forEachIndexed { index, partsEntity ->
            if (partsEntity.isChecked) {
                partsEntity.isChecked = false
                leftAdapter.notifyItemChanged(index)
            }
        }
        rightList.forEachIndexed { index, partsEntity ->
            if (partsEntity.isChecked) {
                partsEntity.isChecked = false
                rightAdapter.notifyItemChanged(index)
            }
        }
        when (adapter) {
            leftAdapter -> {
                leftList[position].isChecked = true
                leftAdapter.notifyItemChanged(position)
                if (listener != null) {
                    listener?.onItemClick(position, leftList[position])
                }
            }
            rightAdapter -> {
                rightList[position].isChecked = true
                rightAdapter.notifyItemChanged(position)
                if (listener != null) {
                    listener?.onItemClick(leftList.size + position, rightList[position])
                }
            }
        }
    }

    /**
     * 监控事件
     */
    interface ItemClickListener {
        fun onItemClick(position: Int, parts: PartsEntity)
    }

    /* ################################## getter setter ##################################*/

    /**
     * 添加
     */
    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    fun setImageUrl(imageUrl: Any) {
        GlideApp.with(mContext).load(imageUrl).into(deviceImage)
    }

    fun addParts(mList: List<PartsEntity>) {
        val size = mList.size
        val half = size / 2
        val median = if (size % 2 == 0) half else half + 1
        mList.forEachIndexed { index, partsEntity ->
            if (index < median) {
                leftAdapter.addData(partsEntity)
            } else {
                rightAdapter.addData(partsEntity)
            }
        }
    }
}

/**
 * 部件
 */
data class PartsEntity(val code: String, val name: String, var isChecked: Boolean)

class PartsListAdapter : BaseQuickAdapter<PartsEntity, BaseViewHolder>(R.layout.item_parts_list, null) {

    var height: Int = 0

    override fun convert(helper: BaseViewHolder, item: PartsEntity) {
        val llyt = helper.getView<LinearLayout>(R.id.llyt)
        val params = llyt?.layoutParams
        params?.height = mContext.dip(220 / itemCount)
        llyt?.layoutParams = params
        helper.setText(R.id.cbName, item.name)
        helper.setChecked(R.id.cbName, item.isChecked)
    }
}