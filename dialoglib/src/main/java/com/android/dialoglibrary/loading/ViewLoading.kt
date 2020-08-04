package com.android.dialoglibrary.loading

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.android.dialoglibrary.R


class ViewLoading private constructor(
    context: Context,
    content: String?,
    private val canNotCancel: Boolean
) : Dialog(context, R.style.Loading) {

    init {
        // 加载布局
        if (content != null && content.isNotEmpty()) {
            setContentView(R.layout.layout_dialog_loading)
            val message = findViewById<TextView>(R.id.message)
            message.text = content
        } else {
            setContentView(R.layout.layout_dialog_loaded)
        }
        val progressImageView = findViewById<ImageView>(R.id.iv_image)
        //创建旋转动画
        val animation: Animation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 2000
        //动画的重复次数
        animation.repeatCount = 10
        //设置为true，动画转化结束后被应用
        animation.fillAfter = true
        //开始动画
        progressImageView.startAnimation(animation)
        // 设置Dialog参数
        val window = window
        if (window != null) {
            val params = window.attributes
            params.gravity = Gravity.CENTER
            window.attributes = params
        }
    }




    companion object {
        private var loadDialog: ViewLoading? = null

        @JvmOverloads
        fun show(
            context: Context,
            message: String? = "",
            isCancel: Boolean = false
        ) {
            if (context is Activity) {
                if (context.isFinishing) {
                    return
                }
            }
            if (loadDialog != null && loadDialog!!.isShowing) {
                return
            }
            loadDialog = ViewLoading(context, message, isCancel)
            loadDialog!!.show()
        }

        fun dismiss(context: Context?) {
            try {
                if (context is Activity) {
                    if (context.isFinishing) {
                        loadDialog = null
                        return
                    }
                }
                if (loadDialog != null && loadDialog!!.isShowing) {
                    val loadContext =
                        loadDialog!!.context
                    if (loadContext is Activity) {
                        if (loadContext.isFinishing) {
                            loadDialog = null
                            return
                        }
                    }
                    loadDialog!!.dismiss()
                    loadDialog = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loadDialog = null
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}