package com.android.dialoglibrary.toast

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import com.android.dialoglibrary.R
import com.android.dialoglibrary.utils.DialogUtils


class ToastUtils private constructor() {
    class Builder(private val context: Context?) {
        private var title: CharSequence? = null
        private var desc: CharSequence? = null
        private var gravity = Gravity.TOP
        private var isFill = false
        private var yOffset = 0
        private var duration = Toast.LENGTH_SHORT
        private var textColor = Color.WHITE
        private var backgroundColor = Color.BLACK
        private var radius = 0f
        private var elevation = 0
        private var layout = 0
        fun setTitle(title: CharSequence?): Builder {
            this.title = title
            return this
        }

        fun setDesc(desc: CharSequence?): Builder {
            this.desc = desc
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setFill(fill: Boolean): Builder {
            isFill = fill
            return this
        }

        fun setOffset(yOffset: Int): Builder {
            this.yOffset = yOffset
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setTextColor(textColor: Int): Builder {
            this.textColor = textColor
            return this
        }

        fun setBackgroundColor(backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun setRadius(radius: Float): Builder {
            this.radius = radius
            return this
        }

        fun setElevation(elevation: Int): Builder {
            this.elevation = elevation
            return this
        }

        fun setLayout(@LayoutRes layout: Int): Builder {
            this.layout = layout
            return this
        }

        fun build(): Toast {
            if (toast == null) {
                toast = Toast(context)
            }
            if (isFill) {
                toast!!.setGravity(gravity or Gravity.FILL_HORIZONTAL, 0, yOffset)
            } else {
                toast!!.setGravity(gravity, 0, yOffset)
            }
            toast!!.duration = duration
            toast!!.setMargin(0f, 0f)
            if (layout == 0) {
                val rootView = LayoutInflater.from(context).inflate(R.layout.view_toast_custom, null,false) as CardView
                val textView = rootView.findViewById<TextView>(R.id.toastTextView)
                val descTv = rootView.findViewById<TextView>(R.id.desc)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //rootView.setElevation(elevation);
                    rootView.cardElevation = elevation.toFloat()
                }
                rootView.radius = radius
                rootView.setCardBackgroundColor(backgroundColor)
                //rootView.setBackgroundColor(backgroundColor);
                textView.setTextColor(textColor)
                textView.text = title
                if (TextUtils.isEmpty(desc)) {
                    descTv.visibility = View.GONE
                } else {
                    descTv.text = desc
                    descTv.visibility = View.VISIBLE
                }
                toast!!.view = rootView
            } else {
                val view = LayoutInflater.from(context).inflate(layout, null,false)
                toast!!.view = view
            }
            return toast!!
        }

    }

    companion object {
        var app: Application? = null

        private var toastBackColor = 0
        val NORMAL_TOAST= 11
        val VIEW_TOAST= 12
//        val TWO_TOAST= 13
        var toastType :Int=0
        fun init(app: Application) {
            this.app = app
            toastBackColor = app.resources.getColor(R.color.color_000000)
        }

        fun setToastBackColor(@ColorInt color: Int) {
            toastBackColor = color
        }

        /**
         * 检查上下文不能为空，必须先进性初始化操作
         */
        private fun checkContext() {
            if (app == null) {
                throw NullPointerException("ToastUtils context is not null，please first init")
            }
        }

        /**
         * 吐司工具类    避免点击多次导致吐司多次，最后导致Toast就长时间关闭不掉了
         * 注意：这里如果传入context会报内存泄漏；传递activity..getApplicationContext()
         * @param content       吐司内容
         */
        private var toast: Toast? = null

        @SuppressLint("ShowToast")
        fun showToast(content: String?) {
            if (toastType != NORMAL_TOAST){
                toast = null
            }
            checkContext()
            if (toast == null){
                toast = Toast.makeText(app, content, Toast.LENGTH_SHORT)
            }else{
                toast!!.setText(content)
            }
            toast!!.show()
            toastType = NORMAL_TOAST
        }

        /**
         * 某些系统可能屏蔽通知
         * 1:检查 SystemUtils.isEnableNotification(BaseApplication.getApplication());
         * 2:替代方案 SnackBarUtils.showSnack(topActivity, noticeStr);
         * 圆角
         * 屏幕中间
         * @param notice                        内容
         */
        fun showRoundRectToast(notice: CharSequence?) {
            checkContext()
            if (TextUtils.isEmpty(notice)) {
                return
            }
            if (toastType != VIEW_TOAST){
                toast = null
            }
            Builder(app)
                .setDuration(Toast.LENGTH_SHORT)
                .setFill(false)
                .setGravity(Gravity.CENTER)
                .setOffset(0)
                .setTitle(notice)
                .setTextColor(Color.WHITE)
                .setBackgroundColor(toastBackColor)
                .setRadius(DialogUtils.dip2px(app, 10f).toFloat())
                .setElevation(DialogUtils.dip2px(app, 0f))
                .build()
                .show()
//            toastType = VIEW_TOAST
        }

        fun showRoundRectToast(
            notice: CharSequence?,
            desc: CharSequence?
        ) {
            checkContext()
            if (TextUtils.isEmpty(notice)) {
                return
            }
            if (toastType != VIEW_TOAST){
                toast = null
            }
            Builder(app)
                .setDuration(Toast.LENGTH_SHORT)
                .setFill(false)
                .setGravity(Gravity.CENTER)
                .setOffset(0)
                .setDesc(desc)
                .setTitle(notice)
                .setTextColor(Color.WHITE)
                .setBackgroundColor(toastBackColor)
                .setRadius(DialogUtils.dip2px(app, 10f).toFloat())
                .setElevation(DialogUtils.dip2px(app, 0f))
                .build()
                .show()
            toastType = VIEW_TOAST
        }

        fun showRoundRectToast(@LayoutRes layout: Int) {
            checkContext()
            if (layout == 0) {
                return
            }
            if (toastType != VIEW_TOAST){
                toast = null
            }
            Builder(app)
                .setDuration(Toast.LENGTH_SHORT)
                .setFill(false)
                .setGravity(Gravity.CENTER)
                .setOffset(0)
                .setLayout(layout)
                .build()
                .show()
            toastType = VIEW_TOAST
        }
    }

    init {
        throw UnsupportedOperationException(" not init ")
    }
}