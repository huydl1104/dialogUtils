package com.android.dialoglibrary.dialogFragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.android.dialoglibrary.R
import com.android.dialoglibrary.toast.ToastUtils

/**
 * 自定义布局弹窗DialogFragment
 */
abstract class BaseDialogFragment : DialogFragment() {
    private var local = Local.BOTTOM

    enum class Local {
        TOP, CENTER, BOTTOM
    }
    private  var mDialog: Dialog?= null
    private var dimAmount = 0.2f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (local == Local.BOTTOM) {
            setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
        } else if (local == Local.CENTER || local == Local.TOP) {
            setStyle(STYLE_NO_TITLE, R.style.CenterDialog)
        }
    }

    companion object {
        private const val TAG = "base_bottom_dialog"


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDialog = dialog
        if (mDialog != null) {
            if (mDialog!!.window != null) {
                mDialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            }
            mDialog!!.setCanceledOnTouchOutside(getIsCancel())
            mDialog!!.setCancelable(getIsCancel())
        }
        val v = inflater.inflate(getLayoutRes(), container, false)
        bindView(v)
        return v
    }

    abstract fun getLayoutRes(): Int
    abstract fun bindView(v: View)
    abstract fun getDimAmount():Float
    abstract fun getIsCancel():Boolean
    abstract fun getHeight():Int
    override fun onStart() {
        super.onStart()

        val window = mDialog?.window
        if (window != null) {
            val params = window.attributes
            params.dimAmount = getDimAmount()
            params.width = WindowManager.LayoutParams.MATCH_PARENT
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



    override fun onDestroy() {
        super.onDestroy()
        mListener?.listener()
    }

    /**
     * 获取弹窗高度
     * @return          int类型值
     */

    open val fragmentTag: String = TAG


    fun setLocal(local: Local) {
        this.local = local
    }

    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            show(fragmentManager, fragmentTag)
        } else {
            ToastUtils.showToast("需要设置setFragmentManager")
        }
    }

    var mListener: onLoadFinishListener? = null

    fun setLoadFinishListener(listener: onLoadFinishListener) {
        mListener = listener
    }

    interface onLoadFinishListener {
        fun listener()
    }



    fun dismissDialog() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }


}