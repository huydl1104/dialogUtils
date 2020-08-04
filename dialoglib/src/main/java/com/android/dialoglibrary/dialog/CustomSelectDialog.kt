package com.android.dialoglibrary.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.dialoglibrary.R

class CustomSelectDialog : Dialog, View.OnClickListener,
    AdapterView.OnItemClickListener {
    private var mListener: SelectDialogListener?=null
    private var mCancelListener: SelectDialogCancelListener? = null
    private var mActivity: Activity
    private var mName: List<String>
    private var mTitle: String? = null
    private var mUseCustomColor = false
    private var mFirstItemColor = 0
    private var mOtherItemColor = 0

    interface SelectDialogListener {
        fun onItemClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        )
    }
    interface SelectDialogCancelListener {
        fun onCancelClick(v: View?)
    }

    constructor(
        activity: Activity,
        theme: Int,
        listener: SelectDialogListener,
        names: List<String>
    ) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mName = names
        setCanceledOnTouchOutside(true)
    }

    constructor(
        activity: Activity,
        theme: Int,
        listener: SelectDialogListener,
        cancelListener: SelectDialogCancelListener?,
        names: List<String>
    ) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mCancelListener = cancelListener
        mName = names
        // 设置是否点击外围不解散
        setCanceledOnTouchOutside(false)
    }

    constructor(
        activity: Activity,
        theme: Int,
        listener: SelectDialogListener,
        names: List<String>,
        title: String?
    ) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mName = names
        mTitle = title
        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true)
    }

    constructor(
        activity: Activity,
        theme: Int,
        listener: SelectDialogListener,
        cancelListener: SelectDialogCancelListener?,
        names: List<String>,
        title: String?
    ) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mCancelListener = cancelListener
        mName = names
        mTitle = title
        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.view_dialog_select, null)
        setContentView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        val window = window
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.BottomDialogAnimationStyle)
            val wl = window.attributes
            wl.x = 0
            wl.y = mActivity.windowManager.defaultDisplay.height
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT
            // 设置显示位置
            onWindowAttributesChanged(wl)
        }
        initViews()
    }

    private fun initViews() {
        val dialogAdapter =
            DialogAdapter(mName)
        val dialogList =
            findViewById<View>(R.id.dialog_list) as ListView
        val mMBtnCancel =
            findViewById<View>(R.id.mBtn_Cancel) as Button
        val mTvTitle = findViewById<View>(R.id.mTv_Title) as TextView
        dialogList.onItemClickListener = this
        dialogList.adapter = dialogAdapter
        mMBtnCancel.setOnClickListener { v ->
            if (mCancelListener != null) {
                mCancelListener!!.onCancelClick(v)
            }
            dismiss()
        }
        if (!TextUtils.isEmpty(mTitle)) {
            mTvTitle.visibility = View.VISIBLE
            mTvTitle.text = mTitle
        } else {
            mTvTitle.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        dismiss()
    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View,
        position: Int,
        id: Long
    ) {
        mListener?.onItemClick(parent, view, position, id)
        dismiss()
    }

    private inner class DialogAdapter internal constructor(private val mStrings: List<String>) : BaseAdapter() {
        private var viewholder: ViewHolder? = null
        override fun getCount(): Int {
            return mStrings.size
        }

        override fun getItem(position: Int): Any {
            return mStrings[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(
            position: Int,
            contentView: View?,
            parent: ViewGroup
        ): View {
            var view = contentView
            if (null == view) {
                viewholder = ViewHolder()
                view = LayoutInflater.from(parent.context).inflate(R.layout.view_dialog_item, null,false)
                viewholder!!.dialogItemButton = view.findViewById<TextView>(R.id.dialog_item_bt)
                view.tag = viewholder
            } else {
                viewholder = view.tag as ViewHolder
            }
            viewholder!!.dialogItemButton!!.text = mStrings[position]
            if (!mUseCustomColor) {
                mFirstItemColor = mActivity.resources.getColor(R.color.grayText)
                mOtherItemColor = mActivity.resources.getColor(R.color.grayText)
            }
            when {
                1 == mStrings.size -> {
                    viewholder!!.dialogItemButton!!.setTextColor(mFirstItemColor)
                    viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.shape_dialog_item_bg_only)
                }
                position == 0 -> {
                    viewholder!!.dialogItemButton!!.setTextColor(mFirstItemColor)
                    viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_top)
                }
                position == mStrings.size - 1 -> {
                    viewholder!!.dialogItemButton!!.setTextColor(mOtherItemColor)
                    viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_buttom)
                }
                else -> {
                    viewholder!!.dialogItemButton!!.setTextColor(mOtherItemColor)
                    viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_center)
                }
            }
            return view!!
        }

    }

    class ViewHolder {
        var dialogItemButton: TextView? = null
    }

    /**
     * 设置列表项的文本颜色
     */
    fun setItemColor(firstItemColor: Int, otherItemColor: Int) {
        mFirstItemColor = firstItemColor
        mOtherItemColor = otherItemColor
        mUseCustomColor = true
    }
}