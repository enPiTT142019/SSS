package com.socu.enpit.sss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.shop_item.view.*
import org.w3c.dom.Text

class ShopInformationAdapter(
    private val mItems:   ArrayList<NewsData>,
    private val mContext: Context
): RecyclerView.Adapter<ShopInformationAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(item: NewsData) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(mContext).inflate(
            R.layout.news_item, parent, false))

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        holder.contents.text = mItems[position].contents
        holder.date.text = mItems[position].date
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.newsTitleText
        val contents: TextView = view.newsContentsText
        val date: TextView = view.newsDayText
    }
}