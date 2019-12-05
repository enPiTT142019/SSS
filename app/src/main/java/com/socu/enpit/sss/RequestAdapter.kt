package com.socu.enpit.sss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.request_item.view.*
import kotlinx.android.synthetic.main.shop_item.view.*
import org.w3c.dom.Text

class RequestAdapter(
    private val mItems:   ArrayList<RequestData>,
    private val mContext: Context
): RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(item: RequestData) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(mContext).inflate(
            R.layout.request_item, parent, false))

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = mItems[position].date
        holder.title.text = mItems[position].title
        holder.contents.text = mItems[position].contents

        // reference: Y.A.M の 雑記帳: SimpleDateFormat ではなく android.text.format.DateFormat を使おう - http://bit.ly/2OybKLu
        //holder.tvCreatedAt.text = DateFormat.format("yyyy/MM/dd kk:mm:ss", mItems[position].mCreatedAt).toString()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val date: TextView = view.requestDayText
        val title: TextView = view.requestTitleText
        val contents: TextView = view.requestContentsText
    }
}