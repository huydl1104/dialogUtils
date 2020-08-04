package com.android.dialoglibrary.dialogMenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.dialoglibrary.R
import java.util.*

internal class DialogAdapter(
    context: Context,
    mItems: List<CustomItem>?,
    layout: Int,
    orientation: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItems: List<CustomItem> = emptyList()
    private var itemClickListener: OnItemClickListener? = null
    private var orientation: Int
    private var layout: Int
    private val context: Context
    private val padding: Int
    private val topPadding: Int
    private val leftPadding: Int
    private val topIcon: Int
    private val leftIcon: Int
    private fun setList(items: List<CustomItem>?) {
        mItems = items ?: ArrayList()
    }

    fun setItemClick(onItemClickListener: OnItemClickListener?) {
        itemClickListener = onItemClickListener
    }

    fun setOrientation(orientation: Int) {
        this.orientation = orientation
        notifyDataSetChanged()
    }

    fun setLayout(layout: Int) {
        this.layout = layout
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when {
            layout == CustomDialog.Companion.GRID -> TopHolder(LinearLayout(parent.context))
            orientation == HORIZONTAL -> TopHolder(
                LinearLayout(parent.context)
            )
            else -> LeftHolder(LinearLayout(parent.context))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = mItems[position]
        val topHolder: TopHolder
        val leftHolder: LeftHolder
        when {
            layout == CustomDialog.Companion.GRID -> {
                topHolder = holder as TopHolder
                topHolder.item.text = item.title
                topHolder.item.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    topHolder.icon(item.icon),
                    null,
                    null
                )
                topHolder.item.setOnClickListener {
                    if (itemClickListener != null) itemClickListener!!.click(
                        item
                    )
                }
            }
            orientation == HORIZONTAL -> {
                topHolder = holder as TopHolder
                topHolder.item.text = item.title
                topHolder.item.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    topHolder.icon(item.icon),
                    null,
                    null
                )
                topHolder.item.setOnClickListener {
                    if (itemClickListener != null) itemClickListener!!.click(
                        item
                    )
                }
            }
            else -> {
                leftHolder = holder as LeftHolder
                leftHolder.item.text = item.title
                leftHolder.item.setCompoundDrawablesWithIntrinsicBounds(
                    leftHolder.icon(item.icon),
                    null,
                    null,
                    null
                )
                leftHolder.item.setOnClickListener {
                    if (itemClickListener != null) itemClickListener!!.click(
                        item
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    private fun getScreenWidth(c: Context): Int {
        return c.resources.displayMetrics.widthPixels
    }

    /**
     * 顶部的
     */
    private inner class TopHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val item: TextView
        fun icon(drawable: Drawable?): Drawable? {
            if (drawable != null) {
                val bitmap = (drawable as BitmapDrawable).bitmap
                var resizeIcon: Drawable = BitmapDrawable(
                    context.resources,
                    Bitmap.createScaledBitmap(bitmap, topIcon, topIcon, true)
                )
                val state = resizeIcon.constantState
                resizeIcon =
                    DrawableCompat.wrap(state?.newDrawable()?.mutate() ?: resizeIcon)
                return resizeIcon
            }
            return null
        }

        init {
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.width = getScreenWidth(context) / 5
            item = TextView(view.context)
            item.layoutParams = params
            item.maxLines = 1
            item.ellipsize = TextUtils.TruncateAt.END
            item.gravity = Gravity.CENTER
            item.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.gray_dark
                )
            )
            item.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.app_normal_margin)
            )
            item.compoundDrawablePadding = topPadding
            item.setPadding(0, padding, 0, padding)
            val typedValue = TypedValue()
            view.context.theme
                .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            item.setBackgroundResource(typedValue.resourceId)
            (view as LinearLayout).addView(item)
        }
    }

    /**
     * 左边的
     */
    private inner class LeftHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val item: TextView
        fun icon(drawable: Drawable?): Drawable? {
            if (drawable != null) {
                val bitmap = (drawable as BitmapDrawable).bitmap
                var resizeIcon: Drawable = BitmapDrawable(
                    context.resources,
                    Bitmap.createScaledBitmap(bitmap, leftIcon, leftIcon, true)
                )
                val state = resizeIcon.constantState
                resizeIcon =
                    DrawableCompat.wrap(state?.newDrawable()?.mutate() ?: resizeIcon)
                return resizeIcon
            }
            return null
        }

        init {
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            view.layoutParams = params
            item = TextView(view.context)
            item.layoutParams = params
            item.maxLines = 1
            item.ellipsize = TextUtils.TruncateAt.END
            item.gravity = Gravity.CENTER_VERTICAL
            item.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.gray_black
                )
            )
            item.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.app_normal_margin)
            )
            item.compoundDrawablePadding = leftPadding
            item.setPadding(padding, padding, padding, padding)
            val typedValue = TypedValue()
            view.context.theme
                .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            item.setBackgroundResource(typedValue.resourceId)
            (view as LinearLayout).addView(item)
        }
    }

    companion object {
        private const val HORIZONTAL = OrientationHelper.HORIZONTAL
        private const val VERTICAL = OrientationHelper.VERTICAL
    }

    init {
        setList(mItems)
        this.context = context
        this.layout = layout
        this.orientation = orientation
        padding = context.resources.getDimensionPixelSize(R.dimen.app_normal_margin)
        topPadding = context.resources.getDimensionPixelSize(R.dimen.app_tiny_margin)
        leftPadding = context.resources.getDimensionPixelSize(R.dimen.app_normal_margin)
        topIcon = context.resources.getDimensionPixelSize(R.dimen.bottom_dialog_top_icon)
        leftIcon = context.resources.getDimensionPixelSize(R.dimen.bottom_dialog_left_icon)
    }
}