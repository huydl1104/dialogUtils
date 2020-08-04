package com.ydl.dialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ydl.dialog.R
import com.ydl.dialog.bean.DialogInfo

class DialogListAdapter(
    private val mContext: Context,
    private val mList: List<DialogInfo>
) : RecyclerView.Adapter<DialogListAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var onItemClickListener: OnAdapterItemClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            inflater.inflate(
                R.layout.item_dialog,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

    fun setOnItemClickListener(onItemClickListener: OnAdapterItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnAdapterItemClickListener {
        fun onItemClick(position: Int)
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}