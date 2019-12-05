package com.socu.enpit.sss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_shop_information.*


class RequestActivity : AppCompatActivity() {

    private val adapter = RequestAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // このActivityで表示させるレイアウトをactivity_request.xmlに設定する。
        setContentView(R.layout.activity_request)

        // レイアウトとRequestAdapterを連携させる
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
        requestRecyclerView.adapter = adapter

        // activity_request.xmlの中のrequestRecyclerViewに
        // CloudDataManagerから受け取った文字列を表示する。
        val requestDataList = CloudDataManager.getRequestDataList()
        for (request in requestDataList) adapter.addItem(request)

        // 戻るボタンを押したときの処理
        back_button.setOnClickListener{ finish() }

        sendButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val contents = contentsEditText.text.toString()
            if (title != "" && contents != "") {

            }
        }
    }
}

