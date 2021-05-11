package com.example.util.simpletimetracker.core.adapter.category

import com.example.util.simpletimetracker.core.R
import com.example.util.simpletimetracker.core.adapter.createRecyclerAdapterDelegate
import com.example.util.simpletimetracker.core.extension.setOnClickWith
import kotlinx.android.synthetic.main.item_category_layout.view.*

fun createCategoryAdapterDelegate(
    onItemClick: ((CategoryViewData) -> Unit)
) = createRecyclerAdapterDelegate<CategoryViewData>(
    R.layout.item_category_layout
) { itemView, item, _ ->

    with(itemView.viewCategoryItem) {
        item as CategoryViewData

        itemColor = item.color
        itemName = item.name
        itemTextColor = item.textColor
        setOnClickWith(item, onItemClick)
    }
}