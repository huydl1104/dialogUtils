package com.android.dialoglibrary.snackbar

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.android.dialoglibrary.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class SnackBarUtils {

    private constructor() {
        throw UnsupportedOperationException("not init ")
    }

    private lateinit var builder: Builder

    private constructor(builder: Builder) {
        this.builder = builder
    }

    private fun make(): Snackbar {
        Log.e("yuyu","builder make 22 ")
        val snackbar = Snackbar.make(builder.view!!, builder.text, builder.duration)
        val layout = snackbar.view as SnackbarLayout
        val actionText = layout.findViewById<Button>(R.id.snackbar_action)
        val text = layout.findViewById<TextView>(R.id.snackbar_text)
        //设置action内容和action点击事件
        if (builder.actionClickListener != null || builder.actionText != null) {
            if (builder.actionClickListener == null) {
                builder.actionClickListener = View.OnClickListener { }
            }
            snackbar.setAction(builder.actionText, builder.actionClickListener)
            if (builder.actionTextColors != null) {
                snackbar.setActionTextColor(builder.actionTextColors)
            } else if (builder.actionTextColor != null) {
                snackbar.setActionTextColor(builder.actionTextColor!!)
            }
        }
        //设置背景
        if (builder.backgroundColor != null) {
            layout.setBackgroundColor(builder.backgroundColor!!)
        }
        //设置action内容文字大小
        if (builder.actionTextSize != null) {
            if (builder.actionTextSizeUnit != null) {
                actionText.setTextSize(builder.actionTextSizeUnit!!, builder.actionTextSize!!)
            } else {
                actionText.textSize = builder.actionTextSize!!
            }
        }
        //设置action文字类型
        var actionTextTypeface = actionText.typeface
        if (builder.actionTextTypeface != null) {
            actionTextTypeface = builder.actionTextTypeface
        }
        if (builder.actionTextTypefaceStyle != null) {
            actionText.setTypeface(actionTextTypeface, builder.actionTextTypefaceStyle!!)
        } else {
            actionText.setTypeface(actionTextTypeface)
        }
        //设置内容字体大小
        if (builder.textSize != null) {
            if (builder.textSizeUnit != null) {
                text.setTextSize(builder.textSizeUnit!!, builder.textSize!!)
            } else {
                text.textSize = builder.textSize!!
            }
        }
        //设置内容字体样式
        var textTypeface = text.typeface
        if (builder.textTypeface != null) textTypeface = builder.textTypeface
        if (builder.textTypefaceStyle != null) {
            text.setTypeface(textTypeface, builder.textTypefaceStyle!!)
        } else {
            text.setTypeface(textTypeface)
        }
        //设置颜色，最大行数和位置属性
        if (builder.textColors != null) {
            text.setTextColor(builder.textColors)
        } else if (builder.textColor != null) {
            text.setTextColor(builder.textColor!!)
        }
        text.maxLines = builder.maxLines
        text.gravity = if (builder.centerText) Gravity.CENTER else Gravity.CENTER_VERTICAL
        if (builder.centerText && Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            text.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        //设置icon
        if (builder.icon != null) {
            var transparentDrawable: Drawable? = null
            if (builder.centerText && TextUtils.isEmpty(builder.actionText)) {
                val conf = Bitmap.Config.ARGB_8888
                val bmp = Bitmap.createBitmap(
                    builder.icon!!.intrinsicWidth,
                    builder.icon!!.intrinsicHeight,
                    conf
                )
                transparentDrawable = BitmapDrawable(builder.view!!.context.resources, bmp)
            }
            text.setCompoundDrawablesWithIntrinsicBounds(builder.icon, null, transparentDrawable,
                null
            )
            text.compoundDrawablePadding = 10
        }
        return snackbar
    }

    class Builder {
        var view: View? = null
        var duration = Snackbar.LENGTH_SHORT
        var text: CharSequence = ""
        private var textResId = 0
        var textColor: Int? = null
        var textColors: ColorStateList? = null
        var textSizeUnit: Int? = null
        var textSize: Float? = null
        var textTypefaceStyle: Int? = null
        var textTypeface: Typeface? = null
        var actionTextSizeUnit: Int? = null
        var actionTextSize: Float? = null
        var actionText: CharSequence? = ""
        private var actionTextResId = 0
        var actionTextTypefaceStyle: Int? = null
        var actionTextTypeface: Typeface? = null
        var actionClickListener: View.OnClickListener? = null
        var actionTextColor: Int? = null
        var actionTextColors: ColorStateList? = null
        var maxLines = Int.MAX_VALUE
        var centerText = false
        var icon: Drawable? = null
        private var iconResId = 0
        var backgroundColor: Int? = null
        fun setActivity(activity: Activity): Builder {
            return setView(
                (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
                    0
                )
            )
        }

        fun setView(view: View?): Builder {
            this.view = view
            return this
        }

        fun setText(@StringRes resId: Int): Builder {
            textResId = resId
            return this
        }

        fun setText(text: CharSequence): Builder {
            textResId = 0
            this.text = text
            return this
        }

        fun setTextColor(@ColorInt color: Int): Builder {
            textColor = color
            return this
        }

        fun setTextColor(colorStateList: ColorStateList?): Builder {
            textColor = null
            textColors = colorStateList
            return this
        }

        fun setTextSize(textSize: Float): Builder {
            textSizeUnit = null
            this.textSize = textSize
            return this
        }

        fun setTextSize(
            unit: Int,
            textSize: Float
        ): Builder {
            textSizeUnit = unit
            this.textSize = textSize
            return this
        }

        fun setTextTypeface(typeface: Typeface?): Builder {
            textTypeface = typeface
            return this
        }

        fun setTextTypefaceStyle(style: Int): Builder {
            textTypefaceStyle = style
            return this
        }

        fun centerText(): Builder {
            centerText = true
            return this
        }

        fun setActionTextColor(colorStateList: ColorStateList?): Builder {
            actionTextColor = null
            actionTextColors = colorStateList
            return this
        }

        fun setActionTextColor(@ColorInt color: Int): Builder {
            actionTextColor = color
            return this
        }

        fun setActionText(@StringRes resId: Int): Builder {
            actionTextResId = resId
            return this
        }

        fun setActionText(text: CharSequence?): Builder {
            textResId = 0
            actionText = text
            return this
        }

        fun setActionTextSize(textSize: Float): Builder {
            actionTextSizeUnit = null
            actionTextSize = textSize
            return this
        }

        fun setActionTextSize(
            unit: Int,
            textSize: Float
        ): Builder {
            actionTextSizeUnit = unit
            actionTextSize = textSize
            return this
        }

        fun setActionTextTypeface(typeface: Typeface?): Builder {
            actionTextTypeface = typeface
            return this
        }

        fun setActionTextTypefaceStyle(style: Int): Builder {
            actionTextTypefaceStyle = style
            return this
        }

        fun setActionClickListener(listener: View.OnClickListener?): Builder {
            actionClickListener = listener
            return this
        }

        fun setMaxLines(maxLines: Int): Builder {
            this.maxLines = maxLines
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setIcon(@DrawableRes resId: Int): Builder {
            iconResId = resId
            return this
        }

        fun setIcon(drawable: Drawable?): Builder {
            icon = drawable
            return this
        }

        fun setBackgroundColor(@ColorInt color: Int): Builder {
            backgroundColor = color
            return this
        }

        fun build(): Snackbar {
            return make()
        }

        private fun make(): Snackbar {
            checkNotNull(view) { "must set an Activity or a View before making a snack" }
            if (textResId != 0) {
                text = view!!.resources.getText(textResId)
            }
            if (actionTextResId != 0) {
                actionText = view!!.resources.getText(actionTextResId)
            }
            if (iconResId != 0) {
                icon = ContextCompat.getDrawable(view!!.context, iconResId)
            }
            return SnackBarUtils(this).make()
        }
    }

    companion object {

        fun showSnackBar(activity: Activity, text: String) {
            builder()
                .setBackgroundColor(activity.resources.getColor(R.color.color_000000))
                .setTextSize(14f)
                .setTextColor(activity.resources.getColor(R.color.white))
                .setTextTypefaceStyle(Typeface.BOLD)
                .setText(text)
                .setMaxLines(1)
                .setActivity(activity)
                .setDuration(Toast.LENGTH_SHORT)
                .build()
                .show()
        }

        fun showSnackBar(
            activity: Activity,
            text: String,
            action: String,
            listener: View.OnClickListener?
        ) {
            builder()
                .setBackgroundColor(activity.resources.getColor(R.color.color_000000))
                .setTextSize(14f)
                .setTextColor(activity.resources.getColor(R.color.white))
                .setTextTypefaceStyle(Typeface.BOLD)
                .setText(text)
                .setMaxLines(1)
                .setActionText(action)
                .setActionTextColor(activity.resources.getColor(R.color.color_f25057))
                .setActionTextSize(14f)
                .setActionTextTypefaceStyle(Typeface.BOLD)
                .setActionClickListener(listener)
                .setActivity(activity)
                .setDuration(Toast.LENGTH_SHORT)
                .build()
                .show()
        }

        fun showSnackBar(
            activity: Activity,
            text: String,
            action: String,
            @DrawableRes resId: Int,
            listener: View.OnClickListener?
        ) {
            builder().setBackgroundColor(activity.resources.getColor(R.color.color_000000))
                .setTextSize(14f)
                .setTextColor(activity.resources.getColor(R.color.white))
                .setTextTypefaceStyle(Typeface.BOLD)
                .setText(text)
                .setMaxLines(1)
                .setActionText(action)
                .setActionTextColor(activity.resources.getColor(R.color.color_f25057))
                .setActionTextSize(14f)
                .setActionTextTypefaceStyle(Typeface.BOLD)
                .setActionClickListener(listener)
                .setActivity(activity)
                .setDuration(Toast.LENGTH_SHORT)
                .setIcon(resId)
                .build()
                .show()
        }

        fun builder(): Builder {
            return Builder()
        }
    }
}