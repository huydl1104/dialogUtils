package com.android.dialoglibrary.dialogFragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager


class BottomDialogFragment : BaseDialogFragment() {

    override var fragmentTag = BottomDialogFragment::class.java.simpleName
    private var mViewListener: ViewListener? = null

    private var mDimAmount: Float = 1f
    private var isCancel: Boolean = false
    private var mHeight :Int =0
    private var layoutRes :Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocal(Local.BOTTOM)
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            layoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES)
            mHeight = savedInstanceState.getInt(KEY_HEIGHT)
            this.mDimAmount = savedInstanceState.getFloat(KEY_DIM)
            isCancel =
                savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE)
        }
    }

    override fun getLayoutRes(): Int = layoutRes

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_LAYOUT_RES, layoutRes)
        outState.putInt(KEY_HEIGHT, mHeight)
        outState.putFloat(KEY_DIM, this.mDimAmount)
        outState.putBoolean(KEY_CANCEL_OUTSIDE, isCancel)
        super.onSaveInstanceState(outState)
    }

    override fun bindView(v: View) {
        mViewListener?.bindView(v)
    }

    override fun getDimAmount(): Float = mDimAmount

    override fun getIsCancel(): Boolean = isCancel


    override fun getHeight(): Int = mHeight



    fun setViewListener(listener: ViewListener): BottomDialogFragment {
        mViewListener = listener
        return this
    }

    fun setLayoutRes(@LayoutRes layoutRes: Int): BottomDialogFragment {
        this.layoutRes = layoutRes
        return this
    }

    fun setCancelOutside(cancel: Boolean): BottomDialogFragment {
        isCancel = cancel
        return this
    }

    fun setTag(tag: String): BottomDialogFragment {
        fragmentTag = tag
        return this
    }

    fun setDimAmount(dim: Float): BottomDialogFragment {
        this.mDimAmount = dim
        return this
    }

    fun setHeight(heightPx: Int): BottomDialogFragment {
        mHeight = heightPx
        return this
    }

    fun show(): BaseDialogFragment {
        show(mFragmentManager)
        return this
    }

    fun dismissDialogFragment() {
        dismissDialog()
    }

    interface ViewListener {
        /**
         * 绑定
         * @param v     view
         */
        fun bindView(v: View)
    }

    companion object {
        private const val KEY_LAYOUT_RES = "bottom_layout_res"
        private const val KEY_HEIGHT = "bottom_height"
        private const val KEY_DIM = "bottom_dim"
        private const val KEY_CANCEL_OUTSIDE = "bottom_cancel_outside"
        private var mFragmentManager: FragmentManager? = null
        fun setFragmentManager(manager: FragmentManager): BottomDialogFragment {
            mFragmentManager = manager
            return BottomDialogFragment()
        }
    }
}