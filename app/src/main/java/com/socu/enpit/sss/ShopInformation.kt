package com.socu.enpit.sss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shop_information.*

class ShopInformation : AppCompatActivity() {

    private val nadapter = ShopInformationNewsAdapter(ArrayList(), this)
    private val madapter = ShopInformationMenuAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_information)
        title = CloudDataManager.getShopName()

        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = nadapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        menuRecyclerView.adapter = madapter

        //shopNameText.text = CloudDataManager.getShopName()
        val sImage = CloudDataManager.getShopImage()
        shopImage.setImageBitmap(sImage)

        val newsDataList = CloudDataManager.getNewsDataList()
        for (news in newsDataList) nadapter.addItem(news)
        val menuDataList = CloudDataManager.getMenuDataList()
        for (menu in menuDataList) madapter.addItem(menu)

        requestButton.setOnClickListener {
            val intent = Intent(applicationContext, RequestActivity::class.java)
            startActivity(intent)
        }
    }
}
