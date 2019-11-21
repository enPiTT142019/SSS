package com.socu.enpit.sss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nifcloud.mbaas.core.NCMB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.shop_item.view.*

class MainActivity : AppCompatActivity() {

    private val adapter =  ShopAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "d33534c7daf258e354c67e100f9923e1be23c347779ef8379e7f8596271ccc29", "f0354f95aba607910c5cb18b8d74d8df858880316963c04ecb9d07f66aef05b2")

        CloudDataManager.setAccountUserNameDefault()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(ShopItemTouchListener(this ,object : ShopItemTouchListener.ItemTouchListener {
            override fun onItemTouch(view: View, position: Int) {
                val success = CloudDataManager.setAccountUserNameFromShopName(view.shopNameText.text.toString())
                if (success) {
                    val intent = Intent(applicationContext, RequestActivity::class.java)
                    startActivity(intent)
                }
                else Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
            }
        }))

        val shopList = CloudDataManager.getShopDataList()
        for (shop in shopList) adapter.addItem(shop)
    }
}
