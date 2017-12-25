package com.saxxhw.library.util

import android.app.Activity
import java.util.*

/**
 * Created by Saxxhw on 2017/12/25.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
class AtyManagerUtil {

    companion object {
        val instance by lazy { AtyManagerUtil() }
        private val mActivities by lazy { LinkedList<Activity>() }
    }

    /**
     * 添加Activity到列表中
     */
    fun addActivity(activity: Activity) {
        mActivities.add(activity)
    }

    /**
     * 从列表中移除当前Activity
     */
    fun removeActivity(activity: Activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity)
        }
    }

    /**
     * 获取当前Activity(列表中最后压入的)
     */
    fun getForwardActivity(): Activity? {
        return if (mActivities.isNotEmpty()) mActivities.last else null
    }

    /**
     * 结束当前Activity
     */
    fun finishActivity() {
        val activity = getForwardActivity()
        if (activity != null) {
            finishActivity(activity)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        mActivities.forEach {
            if (cls == it.javaClass) {
                finishActivity(it)
            }
        }
    }

    /**
     * 结束全部Activity
     */
    fun finishAllActivities() {
        mActivities.forEach {
            finishActivity(it)
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(vararg activities: Activity) {
        activities.forEach {
            mActivities.remove(it)
            it.finish()
        }
    }
}