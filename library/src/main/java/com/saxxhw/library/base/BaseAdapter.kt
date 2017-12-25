package com.saxxhw.library.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Saxxhw on 2017/1/17.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
abstract class BaseAdapter<E>(context: Context) : android.widget.BaseAdapter() {

    protected var mInflater: LayoutInflater = LayoutInflater.from(context)

    private val mList = ArrayList<E>()

    private var canClickItem: MutableMap<Int, onInternalClickListener<E>>? = null

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): E {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 添加数据
     *
     * @param e 添加对象
     */
    fun addItem(e: E) {
        mList.add(e)
    }

    /**
     * 在指定索引位置添加数据
     *
     * @param location 索引
     * @param e        数据
     */
    fun addItem(location: Int, e: E) {
        mList.add(location, e)
    }

    /**
     * 以集合的形式添加数据
     *
     * @param collection
     */
    fun addItems(collection: List<E>) {
        mList.addAll(collection)
    }

    /**
     * 在指定索引位置添加数据集合
     *
     * @param location   索引
     * @param collection 集合
     */
    fun addItems(location: Int, collection: List<E>) {
        mList.addAll(location, collection)
    }

    /**
     * 移除指定对象数据
     *
     * @param e 被移除对象
     */
    fun removeItem(e: E) {
        mList.remove(e)
    }

    /**
     * 移除指定索引位置处的对象
     *
     * @param location 索引
     */
    fun removeItem(location: Int) {
        mList.removeAt(location)
    }

    /**
     * 移除指定对象集合
     *
     * @param collection 待移除集合
     */
    fun removeItems(collection: List<E>) {
        mList.removeAll(collection)
    }

    /**
     * 清空数据集
     */
    fun clear() {
        mList.clear()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        convertView = bindView(position, convertView, parent)
        if (!mList.isEmpty() && position < mList.size) {
            addInternalClickListener(convertView, position, mList[position])
        }
        return convertView
    }

    abstract fun bindView(position: Int, convertView: View, parent: ViewGroup): View

    /**
     * 绑定监听事件
     *
     * @param key             控件id
     * @param onClickListener
     */
    fun setOnInViewClickListener(key: Int, onClickListener: onInternalClickListener<E>) {
        if (canClickItem == null)
            canClickItem = HashMap()
        canClickItem?.put(key, onClickListener)
    }

    private fun addInternalClickListener(itemV: View, position: Int?, valuesMap: E) {
        if (canClickItem != null) {
            for (key in canClickItem!!.keys) {
                val inView = itemV.findViewById<View>(key)
                val listener = canClickItem!![key]
                if (inView != null && listener != null) {
                    inView.setOnClickListener { v -> listener.onClickListener(itemV, v, position, valuesMap) }
                }
            }
        }
    }

    interface onInternalClickListener<in T> {
        fun onClickListener(parentV: View, v: View, position: Int?, values: T)
    }
}
