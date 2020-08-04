package com.android.dialoglibrary.dialogFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.android.dialoglibrary.R
import com.android.dialoglibrary.toast.ToastUtils

abstract class BaseDialog : DialogFragment {
    private var local = Local.BOTTOM
    private val mContentViewv: View?
    var width = 0

    enum class Local {
        TOP, CENTER, BOTTOM
    }
    constructor(context:Context):super(){
        mContentViewv = LayoutInflater.from(context).inflate(getLayoutView(),null,false)
        bindView(mContentViewv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (local == Local.BOTTOM) {
            setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
        } else if (local == Local.CENTER) {
            setStyle(STYLE_NO_TITLE, R.style.CenterDialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null) {
            if (dialog!!.window != null) {
                dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            }
            dialog!!.setCanceledOnTouchOutside(getIsCancel())
            dialog!!.setCancelable(getIsCancel())
        }
        return mContentViewv
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window = dialog!!.window
        val params: WindowManager.LayoutParams
        if (window != null) {
            params = window.attributes
            params.dimAmount = dimAmount
            if (width > 0) {
                params.width = width
            } else {
                params.width = WindowManager.LayoutParams.MATCH_PARENT
            }
            if (getHeight() > 0) {
                params.height = getHeight()
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            if (local == Local.TOP) {
                params.gravity = Gravity.TOP
            } else if (local == Local.CENTER) {
                params.gravity = Gravity.CENTER
            } else {
                params.gravity = Gravity.BOTTOM
            }
            window.attributes = params
        }
    }


    abstract fun getLayoutView(): Int
    abstract fun getHeight():Int
    abstract fun bindView(v: View)
    abstract fun getIsCancel():Boolean


    fun dialogClose() {
        val dialog = dialog
        dialog?.dismiss()
    }

    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            show(fragmentManager, fragmentTag)
        } else {
            ToastUtils.showToast("not init ")
        }
    }

    fun setLocal(local: Local) {
        this.local = local
    }

    override fun onDestroy() {
        super.onDestroy()
        mListener?.destroyDialog()
        if (mContentViewv != null) {
            val vp = mContentViewv.parent
            if (vp != null) {
                (vp as ViewGroup).removeView(mContentViewv)
            }
        }
    }

    var mListener: onDestroyListener? = null
    fun setDestroyListener(listener: onDestroyListener?) {
        mListener = listener
    }

    interface onDestroyListener {
        fun destroyDialog()
    }

    companion object {
        const val fragmentTag = "base_bottom_dialog"
        const val dimAmount = 0.2f
    }

}