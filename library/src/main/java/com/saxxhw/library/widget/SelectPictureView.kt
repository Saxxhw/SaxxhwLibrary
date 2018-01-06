package com.saxxhw.library.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import com.saxxhw.library.R
import com.saxxhw.library.base.BaseAdapter
import com.saxxhw.library.glide.GlideApp
import org.jetbrains.anko.find

/**
 * Created by Saxxhw on 2018/1/1.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
class SelectPictureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : GridView(context, attrs, defStyleAttr), AdapterView.OnItemClickListener, BaseAdapter.onInternalClickListener<String> {

    companion object {
        // 默认数量
        private val DEFAULT_MAX_VALUE = 5
    }

    // 最多可添加张数
    private val maxValue: Int
    // 是否只用来展示图片
    private val showAdd: Boolean

    // 图片列表
    private var mImageList: MutableList<String>? = null

    // 数据列表适配器
    private lateinit var addPicAdapter: AddPicAdapter

    // 增加按钮点击事件监听
    private var pictureSelectListener: onPictureSelectListener? = null

    // 图片移除点击事件监听器
    private var pictureRemovedListener: onPictureRemoveListener? = null

    /**
     * 获取已选中图片列表
     *
     * @return
     */
    val images: List<String>
        get() = addPicAdapter.list

    /**
     * 获取缺少图片数
     *
     * @return
     */
    val missingCount: Int
        get() = maxValue - addPicAdapter.count

    init {
        numColumns = 4
        //初始化属性
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectPicturesView)
        maxValue = mTypedArray.getInteger(R.styleable.SelectPicturesView_spv_max_value, DEFAULT_MAX_VALUE)
        showAdd = mTypedArray.getBoolean(R.styleable.SelectPicturesView_spv_show_add, true)
        mTypedArray.recycle()

        init(context)
        bindListener()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

    /**
     * 初始化
     *
     * @param mContext
     */
    private fun init(mContext: Context) {
        mImageList = ArrayList()
        addPicAdapter = AddPicAdapter(mContext)
        addPicAdapter.setMaxValue(maxValue)
        addPicAdapter.setShowAdd(showAdd)
        adapter = addPicAdapter
    }

    /**
     * 绑定监听器
     */
    private fun bindListener() {
        onItemClickListener = this
        addPicAdapter.setOnInViewClickListener(R.id.iv_delete, this)
    }

    /**
     * 绑定图片选择按钮监听器
     *
     * @param pictureSelectListener
     */
    fun setOnPictureSelectListener(pictureSelectListener: onPictureSelectListener) {
        this.pictureSelectListener = pictureSelectListener
    }

    /**
     * 绑定图片移除监听器
     */
    fun setOnPictureRemovedListener(listener: onPictureRemoveListener) {
        this.pictureRemovedListener = listener
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
        if (!showAdd) {
            return
        }
        val size = addPicAdapter.list.size
        if (size < maxValue && position == size) {
            if (null != pictureSelectListener) {
                pictureSelectListener?.onPictureSelect()
            }
        }
    }

    override fun OnClickListener(parentV: View?, v: View?, position: Int, values: String) {
        addPicAdapter.removeItem(position)
        addPicAdapter.notifyDataSetChanged()
        if (pictureRemovedListener != null) {
            pictureRemovedListener?.onPictureRemoved(position, values)
        }
    }

    interface onPictureSelectListener {
        fun onPictureSelect()
    }

    interface onPictureRemoveListener {
        fun onPictureRemoved(position: Int, item: String)
    }

    // *************************************** get、set方法 ***************************************
    /**
     * 填充图片展示列表
     *
     * @param imageList
     */
    fun setData(imageList: List<String>) {
        mImageList?.clear()
        if (mImageList!!.addAll(imageList)) {
            addPicAdapter.addItems(mImageList!!)
            addPicAdapter.notifyDataSetChanged()
        }
    }
}

// *************************************** 适配器 ***************************************
private class AddPicAdapter internal constructor(context: Context) : BaseAdapter<String>(context) {

    private var imageNum: Int = 0

    private var showAdd: Boolean = false

    internal fun setMaxValue(imageNum: Int) {
        this.imageNum = imageNum
    }

    internal fun setShowAdd(showAdd: Boolean) {
        this.showAdd = showAdd
    }

    override fun getCount(): Int {
        val size = list.size
        return if (showAdd && size < imageNum) {
            size + 1
        } else {
            size
        }
    }

    override fun bindView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.widget_item_select_pictures, parent, false)
            holder = ViewHolder()
            holder.pic = convertView.find(R.id.iv_pic)
            holder.ivDelete = convertView.find(R.id.iv_delete)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        // 展示添加按钮
        if (showAdd) {
            val size = list.size
            if (size < imageNum) {
                if (position < size) {
                    GlideApp.with(context).load(getItem(position)).into(holder.pic!!)
                    if (!getItem(position).contains("http")) {
                        holder.ivDelete!!.visibility = View.VISIBLE
                    }
                } else {
                    GlideApp.with(context).load(R.mipmap.ic_add_pictures).into(holder.pic!!)
                    holder.ivDelete!!.visibility = View.INVISIBLE
                }
            } else {
                GlideApp.with(context).load(getItem(position)).into(holder.pic!!)
                if (!getItem(position).contains("http")) {
                    holder.ivDelete!!.visibility = View.VISIBLE
                }
            }
        }
        // 不展示添加按钮
        else {
            GlideApp.with(context).load(getItem(position)).into(holder.pic!!)
        }

        return convertView!!
    }

    internal class ViewHolder {
        var pic: ImageView? = null
        var ivDelete: ImageView? = null
    }
}