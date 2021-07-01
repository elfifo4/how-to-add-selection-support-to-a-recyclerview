package com.tutsplus.recyclerviewselectiontutorial

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val listItems: List<Person>,
    private val context: Context
) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var tracker: SelectionTracker<Long>

    override fun getItemCount(): Int = listItems.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun onBindViewHolder(vh: MyViewHolder, position: Int) {
        val (name, phone) = listItems[position]
        vh.name.text = name
        vh.phone.text = phone

        val parent = vh.name.parent as LinearLayout

        parent.background = when {
            tracker.isSelected(position.toLong()) -> ColorDrawable(Color.CYAN)
            else -> ColorDrawable(Color.WHITE)
        }
    }

    fun setTracker(tracker: SelectionTracker<Long>) {
        this.tracker = tracker
    }
}