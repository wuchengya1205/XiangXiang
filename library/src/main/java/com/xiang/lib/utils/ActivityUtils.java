package com.xiang.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Stack;
import java.util.WeakHashMap;

public class ActivityUtils {

    private static Stack<WeakReference<Activity>> activityStack;
    private static WeakHashMap<Activity, String> activityName;
    private static ActivityUtils instance;

    private ActivityUtils() {
    }

    public static synchronized ActivityUtils getInstance() {
        if (instance == null) {
            instance = new ActivityUtils();
            if (activityStack == null) {
                activityStack = new Stack<WeakReference<Activity>>();
            }
            if (activityName == null) {
                activityName = new WeakHashMap<>();
            }
        }
        return instance;
    }

    /**
     * 返回当前Activity堆栈
     */
    public Stack<WeakReference<Activity>> getActivityStack() {
        return activityStack;
    }

    /**
     * 返回当前栈顶的activity
     * 没有则返回null
     *
     * @return
     */
    public Activity currentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        return activityStack.lastElement().get();
    }


    /**
     * 栈内是否包含此activity
     *
     * @param cls
     * @return
     */
    public boolean isContains(Class<?> cls) {
        for (WeakReference<Activity> curItem : activityStack) {
            Activity activity = curItem.get();
            if (activity == null) continue;
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 栈内是否包含此activity
     *
     * @param a
     * @return
     */
    public boolean isContains(Activity a) {
        for (WeakReference<Activity> curItem : activityStack) {
            Activity activity = curItem.get();
            if (activity == null) continue;
            if (activity.equals(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * activity入栈
     * 一般在baseActivity的onCreate里面加入
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        pushActivity(activity, activity.getClass().getName());
    }

    public void pushActivity(Activity activity, String winName) {
        activityStack.add(new WeakReference<>(activity));
        activityName.put(activity, winName);
    }


    /**
     * 移除栈顶第一个activity
     */
    public void popTopActivity() {
        Activity activity = activityStack.lastElement().get();
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
            activityName.remove(activity);
        }
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
    public void popActivity(Activity act) {
        if (act == null) return;
        Iterator<WeakReference<Activity>> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> next = iterator.next();
            Activity activity = next.get();
            if (activity != null && act == activity && !activity.isFinishing()) {
                iterator.remove();
                activity.finish();
                activityName.remove(activity);
            }
        }
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
    public void popActivity(Class<?> cls) {
        Iterator<WeakReference<Activity>> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> next = iterator.next();
            Activity activity = next.get();
            if (activity != null && activity.getClass().equals(cls) && !activity.isFinishing()) {
                iterator.remove();
                activity.finish();
                activityName.remove(activity);
            }
        }
    }

    public void popActivityUntilName(String name) {
        while (true) {
            Activity activity = currentActivity();
            String winName = activityName.get(activity);
            if (activity == null) {
                break;
            }
            if (equalsIgnoreCase(winName, name)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 从栈顶往下移除 直到cls这个activity为止
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 还有AB存在
     * <p>
     * 注意此方法 会把自身也finish掉
     *
     * @param cls
     */
    public void popAllActivityUntillOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 所有的栈元素 除了 cls的留下 其他全部移除
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 只有B存在
     * 注意此方法 会把自身也finish掉
     */
    public void popAllActivityExceptOne(Class cls) {
        Iterator<WeakReference<Activity>> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> next = iterator.next();
            Activity activity = next.get();
            if (activity != null && !activity.getClass().equals(cls) && !activity.isFinishing()) {
                iterator.remove();
                activity.finish();
                activityName.remove(activity);
            }
        }
    }

    /**
     * 移除所有的activity
     * 退出应用的时候可以调用
     * （非杀死进程）
     */
    public void popAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            WeakReference<Activity> weakReference = activityStack.get(i);
            if (null != weakReference) {
                Activity activity = activityStack.get(i).get();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activityStack.clear();
        activityName.clear();
    }


    /**
     * 获得现在栈内还有多少activity
     *
     * @return
     */
    public int getCount() {
        if (activityStack != null) {
            return activityStack.size();
        }
        return 0;
    }


    public boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


}
