package com.tutsplus.recyclerviewselectiontutorial

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import kotlinx.android.synthetic.main.list_item.view.*

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.list_item_name
    val phone: TextView = view.list_item_phone

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object: ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long = itemId
            }
}