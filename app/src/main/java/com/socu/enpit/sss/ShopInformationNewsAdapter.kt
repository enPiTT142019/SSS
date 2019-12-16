package com.socu.enpit.sss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*

class ShopInformationNewsAdapter(
    private val nItems:   ArrayList<NewsData>,
    private val nContext: Context
): RecyclerView.Adapter<ShopInformationNewsAdapter.ViewHolder>() {

    override fun getItemCount(): Int = nItems.size

    fun addItem(item: NewsData) {
        nItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(nContext).inflate(
            R.layout.news_item, parent, false
        )
    )

    private fun removeItem(position: Int) {
        nItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        nItems.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = nItems[position].title
        holder.contents.text = nItems[position].contents
        holder.date.text = nItems[position].date
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.newsTitleText
        val contents: TextView = view.newsContentsText
        val date: TextView = view.newsDayText
    }
}