package com.android.dialoglibrary.popupWindow

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow
import androidx.annotation.RequiresApi

/**
 * 自定义PopupWindow控件
 */
class CustomPopupWindow : PopupWindow.OnDismissListener {
    private var mContext :Activity?= null
    private var mResLayoutId = 0
    private var mContentView: View? = null
    private var mAnimationStyle = 0
    private var mWidth = 0
    private var mHeight = 0
    private var mIsFocusable = false
    private var mIsOutside = false
    private var mPopupWindow: PopupWindow? = null
    private var mClippEnable = false
    private var mIgnoreCheekPress = false
    private var mInputMode = 0
    private var mSoftInputMode = 0
    private var mTouchable = false
    private var mIsBackgroundDark = false
    private var mBackgroundDrakValue = 0f
    private var mOnTouchListener: OnTouchListener? = null
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    private var mWindow:Window?= null

    constructor(context:Activity){
        this.mContext=context
        this.mResLayoutId = -1
        this.mAnimationStyle = -1
        this.mIsFocusable = true
        this.mIsOutside = true
        this.mClippEnable = true
        this.mIgnoreCheekPress = false
        this.mInputMode = -1
        this.mSoftInputMode = -1
        this.mTouchable = true
        this.mIsBackgroundDark = false
        this.mBackgroundDrakValue = 0.0f
    }

    fun getHeight(): Int {
        return mHeight
    }

    override fun onDismiss() {
        this.dismiss()
    }

    fun dismiss() {
        mOnDismissListener?.onDismiss()
        if (mWindow != null) {
            val params = mWindow!!.attributes
            params.alpha = 1.0f
            mWindow!!.attributes = params
        }
        if (mPopupWindow != null && mPopupWindow!!.isShowing) {
            mPopupWindow!!.dismiss()
        }
    }

    class PopupWindowBuilder(val context: Activity) {

        private val mCustomPopWindow: CustomPopupWindow = CustomPopupWindow(context)

        fun create(): CustomPopupWindow {
            mCustomPopWindow.build()
            return mCustomPopWindow
        }
        /**
         * 设置布局
         * @param resLayoutId       资源文件
         */
        fun setView(resLayoutId: Int): PopupWindowBuilder {
            mCustomPopWindow.mResLayoutId = resLayoutId
            mCustomPopWindow.mContentView = null
            return this
        }

        /**
         * 设置布局
         * @param view              view
         */
        fun setView(view: View?): PopupWindowBuilder {
            mCustomPopWindow.mContentView = view
            mCustomPopWindow.mResLayoutId = -1
            return this
        }

        /**
         * 设置动画
         * @param animationStyle   资源文件
         */
        fun setAnimationStyle(animationStyle: Int): PopupWindowBuilder {
            mCustomPopWindow.mAnimationStyle = animationStyle
            return this
        }

        /**
         * 设置大小
         * @param width             宽
         * @param height            高
         */
        fun size(width: Int, height: Int): PopupWindowBuilder {
            mCustomPopWindow.mWidth = width
            mCustomPopWindow.mHeight = height
            return this
        }

        /**
         * 设置是否可以设置焦点
         * @param focusable         布尔
         */
        fun setFocusable(focusable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsFocusable = focusable
            return this
        }

        /**
         * 设置是否可以点击弹窗外部
         * @param outsideTouchable  布尔
         */
        fun setOutsideTouchable(outsideTouchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsOutside = outsideTouchable
            return this
        }

        /**
         * 设置是否可以裁剪
         * @param enable            布尔
         */
        fun setClippingEnable(enable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mClippEnable = enable
            return this
        }

        /**
         * 设置是否忽略按下
         * @param ignoreCheekPress  布尔
         */
        fun setIgnoreCheekPress(ignoreCheekPress: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress
            return this
        }

        /**
         * 设置类型
         * @param mode              类型
         */
        fun setInputMethodMode(mode: Int): PopupWindowBuilder {
            mCustomPopWindow.mInputMode = mode
            return this
        }

        /**
         * 设置弹窗关闭监听
         * @param onDismissListener listener
         */
        fun setOnDissmissListener(onDismissListener: PopupWindow.OnDismissListener?): PopupWindowBuilder {
            mCustomPopWindow.mOnDismissListener = onDismissListener
            return this
        }

        /**
         * 设置类型
         * @param softInputMode
         */
        fun setSoftInputMode(softInputMode: Int): PopupWindowBuilder {
            mCustomPopWindow.mSoftInputMode = softInputMode
            return this
        }

        /**
         * 设置是否可以触摸
         * @param touchable     布尔
         */
        fun setTouchable(touchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mTouchable = touchable
            return this
        }

        /**
         * 设置触摸拦截
         * @param touchIntercepter  拦截器
         */
        fun setTouchIntercepter(touchIntercepter: OnTouchListener?): PopupWindowBuilder {
            mCustomPopWindow.mOnTouchListener = touchIntercepter
            return this
        }

        /**
         * 设置是否点击背景变暗
         * @param isDark        布尔
         */
        fun enableBackgroundDark(isDark: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsBackgroundDark = isDark
            return this
        }

        /**
         * 设置背景
         * @param darkValue     值
         */
        fun setBgDarkAlpha(darkValue: Float): PopupWindowBuilder {
            mCustomPopWindow.mBackgroundDrakValue = darkValue
            return this
        }

    }

    private fun build(): PopupWindow {
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null,false)
        }
        //获取Activity对象
        if (mIsBackgroundDark) { //设置背景透明度
            val alpha = if (mBackgroundDrakValue > 0.0f && mBackgroundDrakValue < 1.0f) mBackgroundDrakValue else 0.7f
            val params = mContext?.window!!.attributes
            params.alpha = alpha
            mContext?.window!!.attributes = params
            mWindow = mContext?.window
        }
        //设置宽高
        mPopupWindow = if (mWidth != 0 && mHeight != 0) {
            PopupWindow(mContentView, mWidth, mHeight)
        } else {
            PopupWindow(mContentView, -2, -2)
        }
        //设置动画
        if (mAnimationStyle != -1) {
            mPopupWindow?.animationStyle = mAnimationStyle
        }
        this.apply(mPopupWindow!!)
        //设置是否捕获焦点，默认为true
        mPopupWindow?.isFocusable = mIsFocusable
        //设置背景，默认是全透明
        mPopupWindow?.setBackgroundDrawable(ColorDrawable(0))
        //设置是否可以点击外部，默认是true
        mPopupWindow?.isOutsideTouchable = mIsOutside
        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow?.contentView?.measure(0, 0)
            mWidth = mPopupWindow?.contentView?.measuredWidth!!
            mHeight = mPopupWindow?.contentView?.measuredHeight!!
        }
        //实现关闭监听
        mPopupWindow?.setOnDismissListener(this)
        mPopupWindow?.update()
        return mPopupWindow!!
    }

    private fun apply(mPopupWindow: PopupWindow?) {
        mPopupWindow!!.isClippingEnabled = mClippEnable
        if (mIgnoreCheekPress) {
            mPopupWindow.setIgnoreCheekPress()
        }
        if (mInputMode != -1) {
            mPopupWindow.inputMethodMode = mInputMode
        }
        if (mSoftInputMode != -1) {
            mPopupWindow.softInputMode = mSoftInputMode
        }
        if (mOnDismissListener != null) {
            mPopupWindow.setOnDismissListener(mOnDismissListener)
        }
        if (mOnTouchListener != null) {
            mPopupWindow.setTouchInterceptor(mOnTouchListener)
        }
        mPopupWindow.isTouchable = mTouchable
    }

    /**
     * 直接展示
     */
    fun showAsDropDown(anchor: View?): CustomPopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor)
        }
        return this
    }

    /**
     * 传入x，y值位置展示
     */
    fun showAsDropDown(anchor: View?, xOff: Int, yOff: Int): CustomPopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff)
        }
        return this
    }

    /**
     * 传入x，y值，和gravity位置展示。是相对坐标的gravity位置
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(
        anchor: View?,
        xOff: Int,
        yOff: Int,
        gravity: Int
    ): CustomPopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff, gravity)
        }
        return this
    }

    /**
     * 传入x，y值，和gravity位置展示。是相对gravity的相对位置
     */
    fun showAtLocation(
        parent: View?,
        gravity: Int,
        x: Int,
        y: Int
    ): CustomPopupWindow {
        mPopupWindow?.showAtLocation(parent, gravity, x, y)
        return this
    }

}