package com.android.dialoglibrary.dialogMenu

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dialoglibrary.R
import java.util.*

class CustomDialog internal constructor(context: Context?) :
    Dialog(context!!, R.style.CustomBottomDialog) {
    private var background: LinearLayout? = null
    private var container: LinearLayout? = null
    private var titleView: TextView? = null
    private var cancel: TextView? = null
    private var adapter: DialogAdapter? = null
    private var orientation = 0
    private var layout = 0

    init {
        setContentView(R.layout.bottom_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        //建议在这里做个非空判断
        if (window != null) {
            window!!.setGravity(Gravity.BOTTOM)
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        background = findViewById<View>(R.id.background) as LinearLayout
        titleView = findViewById<View>(R.id.title) as TextView
        container = findViewById<View>(R.id.container) as LinearLayout
        cancel = findViewById<View>(R.id.cancel) as TextView
        cancel!!.setOnClickListener { dismiss() }
    }

    fun title(title: Int) {
        title(context.getString(title))
    }

    fun title(title: String?) {
        titleView!!.text = title
        titleView!!.visibility = View.VISIBLE
    }

    fun setCancel(isShow: Boolean, text: String?) {
        if (isShow) {
            cancel!!.visibility = View.VISIBLE
            cancel!!.text = text
        } else {
            cancel!!.visibility = View.GONE
        }
    }

    fun layout(layout: Int) {
        this.layout = layout
        if (adapter != null) adapter!!.setLayout(layout)
    }

    fun orientation(orientation: Int) {
        this.orientation = orientation
        if (adapter != null) adapter!!.setOrientation(orientation)
    }

    fun background(res: Int) {
        background!!.setBackgroundResource(res)
    }

    @SuppressLint("RestrictedApi")
    fun inflateMenu(
        menu: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        val menuInflater: MenuInflater = SupportMenuInflater(context)
        val menuBuilder = MenuBuilder(context)
        menuInflater.inflate(menu, menuBuilder)
        val items: MutableList<CustomItem> = ArrayList()
        for (i in 0 until menuBuilder.size()) {
            val menuItem = menuBuilder.getItem(i)
            items.add(
                CustomItem(
                    menuItem.itemId,
                    menuItem.title.toString(),
                    menuItem.icon
                )
            )
        }
        addItems(items, onItemClickListener)
    }

    fun addItems(
        items: List<CustomItem>?,
        onItemClickListener: OnItemClickListener?
    ) {
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        adapter = DialogAdapter(
            context,
            items,
            layout,
            orientation
        )
        adapter!!.setItemClick(onItemClickListener)
        val manager: RecyclerView.LayoutManager = when (layout) {
            LINEAR -> LinearLayoutManager(
                context,
                orientation,
                false
            )
            GRID -> GridLayoutManager(
                context,
                5,
                orientation,
                false
            )
            else -> LinearLayoutManager(context, orientation, false)
        }
        val recyclerView = RecyclerView(context)
        recyclerView.layoutParams = params
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        container!!.addView(recyclerView)
    }

    fun setItemClick(onItemClickListener: OnItemClickListener?) {
        adapter!!.setItemClick(onItemClickListener)
    }

    companion object {
        const val LINEAR = 0
        const val GRID = 1
    }


}