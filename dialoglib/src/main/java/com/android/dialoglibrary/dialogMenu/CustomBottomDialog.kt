package com.android.dialoglibrary.dialogMenu

import android.content.Context
import androidx.recyclerview.widget.OrientationHelper

class CustomBottomDialog(context: Context) {
    private val customDialog: CustomDialog = CustomDialog(context)
    fun title(title: String): CustomBottomDialog {
        customDialog.title(title)
        return this
    }

    fun title(title: Int): CustomBottomDialog {
        customDialog.title(title)
        return this
    }

    fun setCancel(isShow: Boolean, text: String): CustomBottomDialog {
        customDialog.setCancel(isShow, text)
        return this
    }

    fun background(res: Int): CustomBottomDialog {
        customDialog.background(res)
        return this
    }

    fun inflateMenu(
        menu: Int,
        onItemClickListener: OnItemClickListener
    ): CustomBottomDialog {
        customDialog.inflateMenu(menu, onItemClickListener)
        return this
    }

    fun layout(layout: Int): CustomBottomDialog {
        customDialog.layout(layout)
        return this
    }

    fun orientation(orientation: Int): CustomBottomDialog {
        customDialog.orientation(orientation)
        return this
    }

    fun addItems(
        items: List<CustomItem>?,
        onItemClickListener: OnItemClickListener
    ): CustomBottomDialog {
        customDialog.addItems(items, onItemClickListener)
        return this
    }

    fun itemClick(listener: OnItemClickListener): CustomBottomDialog {
        customDialog.setItemClick(listener)
        return this
    }

    fun show() {
        customDialog.show()
    }

    companion object {
        const val HORIZONTAL = OrientationHelper.HORIZONTAL
        const val VERTICAL = OrientationHelper.VERTICAL
    }

}