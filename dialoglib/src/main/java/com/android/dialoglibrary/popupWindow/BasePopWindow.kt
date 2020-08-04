package com.android.dialoglibrary.popupWindow

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.android.dialoglibrary.utils.DialogUtils


abstract class BasePopWindow: PopupWindow{

    private var mContext: Context? = null
    private var mContentView: View? = null
    constructor(context: Context):super(context){
        mContext = context
        initView()
        this.initData(contentView)
    }

   private fun initView() {
        mContentView = LayoutInflater.from(mContext).inflate(getLayoutView(), null,false)
        contentView = mContentView
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.setBackgroundDrawable(BitmapDrawable())
        /*ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);*/
        isOutsideTouchable = false
        isFocusable = true // 可获得焦点
        isTouchable = true // 可触摸
        this.initData(contentView)
    }

    /**
     * 这两个抽象方法子类必须实现
     */
    abstract fun getLayoutView():Int

    abstract fun initData(contentView: View)
    /**
     * 设置屏幕透明度
     * @param bgAlpha               透明度
     */
    fun setBgAlpha(bgAlpha: Float) {
        DialogUtils.setBackgroundAlpha(mContext as Activity, bgAlpha)
    }

    /**
     * 设置延迟
     * @param time 毫秒
     */
    fun setDelayedMsDismiss(time: Long) {
        mHandler!!.postDelayed(delayedRun!!, time)
    }

    override fun dismiss() {
        super.dismiss()
        DialogUtils.setBackgroundAlpha(mContext as Activity, 1f)
        if (mHandler != null) {
            if (delayedRun != null) {
                mHandler!!.removeCallbacks(delayedRun)
            } else {
                mHandler!!.removeCallbacksAndMessages(null)
            }
            mHandler = null
        }
    }

    override fun getWidth(): Int {
        if (mContentView == null) {
            return 0
        }
        mContentView!!.measure(0, 0)
        return mContentView!!.measuredWidth
    }

    override fun getHeight(): Int {
        if (mContentView == null) {
            return 0
        }
        mContentView!!.measure(0, 0)
        return mContentView!!.measuredHeight
    }

    private val delayedRun: Runnable? = Runnable { dismiss() }
    private var mHandler: Handler? = Handler()

}