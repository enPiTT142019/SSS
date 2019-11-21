package com.socu.enpit.sss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shop_item.view.*

class ShopAdapter(
    private val mItems:   ArrayList<ShopData>,
    private val mContext: Context
): RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(item: ShopData) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(mContext).inflate(
        R.layout.shop_item, parent, false))

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shopName.text = mItems[position].shopName

        // reference: Y.A.M の 雑記帳: SimpleDateFormat ではなく android.text.format.DateFormat を使おう - http://bit.ly/2OybKLu
        //holder.tvCreatedAt.text = DateFormat.format("yyyy/MM/dd kk:mm:ss", mItems[position].mCreatedAt).toString()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val shopName: TextView = view.shopNameText
    }
}