package com.example.util.simpletimetracker.core.di.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    protected val delegates: MutableMap<Int, BaseRecyclerAdapterDelegate> =
        mutableMapOf()

    private val items: MutableList<ViewHolderType> =
        mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return delegates[viewType]!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int =
        items.size

    override fun getItemViewType(position: Int): Int =
        items[position].getViewType()

    fun replace(newItems: List<ViewHolderType>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun add(newItem: ViewHolderType) {
        items.add(newItem)
        notifyItemInserted(items.lastIndex)
    }
}