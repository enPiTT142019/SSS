package com.socu.enpit.sss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shop_information.*

class ShopInformation : AppCompatActivity() {

    private val nadapter = ShopInformationAdapter(ArrayList(), this)
    private val madapter = ShopInformationAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_information)

        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = nadapter
        menuRecyclerView.adapter = madapter

        shopNameText.text = CloudDataManager.getShopName()
        val newsDataList = CloudDataManager.getNewsDataList()
        for (news in newsDataList) nadapter.addItem(news)
        val menuDataList = CloudDataManager.getNewsDataList()
        for (menu in menuDataList) madapter.addItem(menu)
    }
}
