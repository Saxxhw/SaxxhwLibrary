package com.saxxhw.library.util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Saxxhw on 2016/11/26.
 * 邮箱：Saxxhw@126.com
 * 功能：Activity栈管理管理工具类
 */

public class AtyManagerUtil {

    private static AtyManagerUtil instance;

    private static List<Activity> mActivities = new LinkedList<>();

    private AtyManagerUtil() {

    }

    public static AtyManagerUtil getInstance() {
        if (null == instance) {
            synchronized (AtyManagerUtil.class) {
                if (null == instance) {
                    instance = new AtyManagerUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到列表中
     */
    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 从列表中移除当前activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * @return 获取长度
     */
    public int size() {
        return mActivities.size();
    }

    /**
     * 获取当前Activity（列表中最后压入的）
     */
    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    /**
     * 结束当前Activity（列表中最后压入的）
     */
    public synchronized void finishActivity() {
        Activity activity = getForwardActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public synchronized void finishActivity(Activity... activity) {
        if (activity != null && activity.length > 0) {
            for (Activity anActivity : activity) {
                Activity aty = anActivity;
                mActivities.remove(aty);
                aty.finish();
                aty = null;
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        for (int i = 0; i < mActivities.size(); i++) {
            Activity activity = mActivities.get(i);
            if (cls.equals(activity.getClass())) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定类名的Activity以外的其他Activity
     */
    public synchronized void finishOthersActivities(Class<?> cls) {
        for (Activity activity : mActivities) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public synchronized void finishAllActivities() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            mActivities.remove(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized boolean hasActivity(String activityName) {
        if (activityName == null)
            return false;
        for (int i = 0; i < mActivities.size(); i++) {
            if (mActivities.get(i).getClass().getName().endsWith(activityName)) {
                return true;
            }
        }
        return false;
    }
}
