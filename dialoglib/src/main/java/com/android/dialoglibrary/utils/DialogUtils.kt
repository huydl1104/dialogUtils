package com.android.dialoglibrary.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import com.android.dialoglibrary.R
import com.android.dialoglibrary.dialogFragment.CustomDialogFragment


object DialogUtils {
    fun dip2px(context: Context?, dpValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
    /**
     * 设置页面的透明度
     * 主要作用于：弹窗时设置宿主Activity的背景色
     * @param bgAlpha 1表示不透明
     */
    fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha
        val window = activity.window
        if (window != null) {
            if (bgAlpha == 1f) { //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else { //此行代码主要是解决在华为手机上半透明效果无效的bug
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            window.attributes = lp
        }
    }

    fun requestMsgPermission(context: Context?) {
        if (context == null) {
            return
        }
        try { // 6.0以上系统才可以判断权限
            if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                val dialogFragment = CustomDialogFragment()
                dialogFragment.setFragmentManager((context as FragmentActivity).supportFragmentManager)
                    .setTitle("")
                    .setContent("开启消息通知能够帮助你查看更多消息哦~")
                    .setCancelContent("下次再说")
                    .setOkContent("立即开启")
                    .setOkColor(context.getResources().getColor(R.color.color_000000))
                    .setCancelOutside(true)
                    .setCancelListener(View.OnClickListener { dialogFragment.dismissDialogFragment() })
                    .setOkListener(View.OnClickListener {
                        dialogFragment.dismissDialogFragment()
                        toSetting(context)
                    })
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 有点问题
     */
    private fun isNotificationEnabled(context: Context): Boolean {
        val CHECK_OP_NO_THROW = "checkOpNoThrow"
        val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
        var mAppOps: AppOpsManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppOps =
                context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        }
        val appInfo = context.applicationInfo
        val pkg = context.applicationContext.packageName
        val uid = appInfo.uid
        val appOpsClass: Class<*>
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsClass = Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod = appOpsClass.getMethod(
                    CHECK_OP_NO_THROW,
                    Integer.TYPE,
                    Integer.TYPE,
                    String::class.java
                )
                val opPostNotificationValue =
                    appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
                val value = opPostNotificationValue[Int::class.java] as Int
                return (checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int
                        == AppOpsManager.MODE_ALLOWED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun toSetting(context: Context) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts(
                "package",
                context.packageName,
                null
            )
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                "com.android.settings",
                "com.android.setting.InstalledAppDetails"
            )
            localIntent.putExtra(
                "com.android.settings.ApplicationPkgName",
                context.packageName
            )
        }
        context.startActivity(localIntent)
    }
}