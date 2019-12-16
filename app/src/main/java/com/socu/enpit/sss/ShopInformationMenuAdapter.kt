package com.socu.enpit.sss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_item.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class ShopInformationMenuAdapter(
    private val mItems:   ArrayList<MenuData>,
    private val mContext: Context
): RecyclerView.Adapter<ShopInformationMenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(item: MenuData) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(mContext).inflate(
            R.layout.menu_item, parent, false
        )
    )

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        mItems.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        holder.contents.text = mItems[position].contents
        holder.date.text = mItems[position].date
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.menuTitleText
        val contents: TextView = view.menuContentsText
        val date: TextView = view.menuDayText
    }
}