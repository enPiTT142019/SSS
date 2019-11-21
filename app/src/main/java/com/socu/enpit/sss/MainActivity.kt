package com.socu.enpit.sss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nifcloud.mbaas.core.NCMB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter =  ShopAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "d33534c7daf258e354c67e100f9923e1be23c347779ef8379e7f8596271ccc29", "f0354f95aba607910c5cb18b8d74d8df858880316963c04ecb9d07f66aef05b2")

        CloudDataManager.setAccountUserName("_everyone")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val shopList = CloudDataManager.getShopDataList()
        for (shop in shopList) adapter.addItem(shop)
    }
}
