package com.android.dialoglibrary.dialogFragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentManager
import com.android.dialoglibrary.R
import com.android.dialoglibrary.toast.ToastUtils
import com.android.dialoglibrary.utils.DialogUtils

open class CustomDialogFragment : BaseDialogFragment() {


    override var fragmentTag = CustomDialogFragment::class.java.simpleName
    private var title: String? = null
    private var content: String? = null
    private var cancelColor = 0
    private var okColor = 0
    private var cancelContent: String? = null
    private var okContent: String? = null
    private var otherContent: String? = null
    private var cancelListener: View.OnClickListener? = null
    private var okListener: View.OnClickListener? = null
    private var otherListener: View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setLocal(Local.CENTER)
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mHeight = savedInstanceState.getInt(KEY_HEIGHT)
            dimAmount = savedInstanceState.getFloat(KEY_DIM)
            isCancel = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE)
        }
    }

    override fun getLayoutRes(): Int = R.layout.view_custom_dialog

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_HEIGHT, mHeight)
        outState.putFloat(KEY_DIM, dimAmount)
        outState.putBoolean(KEY_CANCEL_OUTSIDE, isCancel)
        super.onSaveInstanceState(outState)
    }


    private var mTvTitle :TextView?= null
    private var mTvContent :TextView?= null
    private var mTvCancel :TextView?= null
    private var mTvOk :TextView?= null
    private var mTvOther :TextView?= null
    override fun bindView(v: View) {
        Log.e("yuyu","bindVIew --> ")
         mTvTitle = v.findViewById<TextView>(R.id.tv_title)
         mTvContent = v.findViewById<TextView>(R.id.tv_content)
         mTvCancel = v.findViewById<TextView>(R.id.tv_cancel)
         mTvOk = v.findViewById<TextView>(R.id.tv_ok)
         mTvOther = v.findViewById<TextView>(R.id.tv_other)
        val mViewLineLeft = v.findViewById(R.id.view_line_left) as View
        val mViewLineRight = v.findViewById(R.id.view_line_right) as View
        if (title != null && title!!.isNotEmpty()) {
            mTvTitle?.visibility = View.VISIBLE
            mTvTitle?.text = title
        } else {
            mTvTitle?.visibility = View.GONE
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = DialogUtils.dip2px(context, 20.0f)
            params.leftMargin = DialogUtils.dip2px(context, 20.0f)
            params.rightMargin = DialogUtils.dip2px(context, 20.0f)
            mTvContent?.layoutParams = params
        }
        if (content != null && content!!.isNotEmpty()) {
            mTvContent?.visibility = View.VISIBLE
            mTvContent?.text = content
        } else {
            mTvContent?.visibility = View.GONE
        }
        if (cancelColor != 0) {
            mTvCancel?.setTextColor(cancelColor)
        } else {
            mTvCancel?.setTextColor(Color.parseColor("#333333"))
        }
        if (okColor != 0) {
            mTvOk?.setTextColor(okColor)
        } else {
            mTvOk?.setTextColor(Color.parseColor("#ff666666"))
        }
        if (cancelContent != null && cancelContent!!.isNotEmpty()) {
            mTvCancel?.text = cancelContent
            mTvCancel?.visibility = View.VISIBLE
            mViewLineLeft.visibility = View.VISIBLE
        } else {
            mTvCancel?.visibility = View.GONE
            mViewLineLeft.visibility = View.GONE
        }
        if (okContent != null && okContent!!.isNotEmpty()) {
            mTvOk?.text = okContent
        } else {
            mTvOk?.text = "确定"
        }
        if (cancelListener != null) {
            mTvCancel?.setOnClickListener(cancelListener)
        }
        if (okListener != null) {
            mTvOk?.setOnClickListener(okListener)
        }
        if (otherContent != null && otherContent!!.isNotEmpty() && otherListener != null) {
            mViewLineRight.visibility = View.VISIBLE
            mTvOther?.visibility = View.VISIBLE
            mTvOther?.setOnClickListener(otherListener)
            dismissDialogFragment()
        } else {
            mViewLineRight.visibility = View.GONE
            mTvOther?.visibility = View.GONE
        }
    }
    private  var isCancel: Boolean = false
    private var mHeight = 0
    private var dimAmount = 0.2f
    override fun getDimAmount(): Float = dimAmount
    override fun getIsCancel(): Boolean = isCancel
    override fun getHeight(): Int = mHeight

    private var mFragmentManager: FragmentManager? = null
    fun setCancelOutside(cancel: Boolean): CustomDialogFragment {
        isCancel = cancel
        return this
    }

    fun setTag(tag: String): CustomDialogFragment {
        fragmentTag = tag
        return this
    }

    fun setDimAmount(dim: Float): CustomDialogFragment {
        dimAmount = dim
        return this
    }

    fun setHeight(heightPx: Int): CustomDialogFragment {
        mHeight = heightPx
        return this
    }

    fun setTitle(title: String): CustomDialogFragment {
        this.title = title
        Log.e("yuyu","setTitle -->$title")
        mTvTitle?.text = title
        return this
    }

    fun setContent(content: String): CustomDialogFragment {
        this.content = content
        mTvContent?.text =content
        return this
    }

    fun setCancelColor(@ColorInt color: Int): CustomDialogFragment {
        cancelColor = color
        mTvCancel?.setTextColor(color)
        return this
    }

    fun setOkColor(@ColorInt color: Int): CustomDialogFragment {
        okColor = color
        mTvOk?.setTextColor(color)
        return this
    }

    fun setCancelContent(content: String?): CustomDialogFragment {
        cancelContent = content
        mTvCancel?.text = content
        return this
    }

    fun setOkContent(content: String): CustomDialogFragment {
        okContent = content
        mTvOk?.text = content
        return this
    }

    fun setOtherContent(content: String): CustomDialogFragment {
        otherContent = content
        mTvOther?.text = content
        return this
    }

    fun setCancelListener(listener: View.OnClickListener): CustomDialogFragment {
        cancelListener = listener
        return this
    }

    fun setOkListener(listener: View.OnClickListener): CustomDialogFragment {
        okListener = listener
        return this
    }

    fun setOtherListener(listener: View.OnClickListener): CustomDialogFragment {
        otherListener = listener
        return this
    }

    fun show(): BaseDialogFragment {
        show(mFragmentManager)
        return this
    }

     open fun setFragmentManager(manager: FragmentManager):CustomDialogFragment {
        mFragmentManager = manager
        return this
    }

    fun dismissDialogFragment() {
        super.dismissDialog()
    }
    companion object {
        private const val KEY_HEIGHT = "bottom_height"
        private const val KEY_DIM = "bottom_dim"
        private const val KEY_CANCEL_OUTSIDE = "bottom_cancel_outside"



    }
}