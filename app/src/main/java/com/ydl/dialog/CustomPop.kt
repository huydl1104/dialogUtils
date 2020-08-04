package com.ydl.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.android.dialoglibrary.popupWindow.BasePopWindow
import com.android.dialoglibrary.toast.ToastUtils.Companion.showRoundRectToast

class CustomPop : BasePopWindow {

    constructor(context: Context):super(context){

    }

    override fun getLayoutView(): Int = R.layout.view_pop_custom_dialog

    override fun initData(contentView: View) {
        val textView = contentView.findViewById<TextView>(R.id.tv_pop)
        textView.setOnClickListener { showRoundRectToast("消失") }
    }

}
