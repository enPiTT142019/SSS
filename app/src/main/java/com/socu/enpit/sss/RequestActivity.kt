package com.socu.enpit.sss

import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_request.*
import java.util.*
import kotlin.collections.ArrayList


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
                val date = DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString()
                val data = RequestData(title, contents, date)
                adapter.addItem(data)
                CloudDataManager.addRequestData(data)
                titleEditText.setText("")
                contentsEditText.setText("")
                Toast.makeText(this, "送信しました。", Toast.LENGTH_SHORT).show()
            }else if(title == "" && contents == ""){
                Toast.makeText(this, "タイトルと内容を書き込んでください。", Toast.LENGTH_SHORT).show()
            }
        }

    }
    // メニューを表示させる処理
    // この関数をオーバーライドして「menu.xml」を指定することで表示される
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // メニューを選択したときの動作をここに書く
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // メニューの「読み込みなおす」を押したとき
            R.id.reload -> {
                adapter.removeAllItems()
                val requestDataList = CloudDataManager.getRequestDataList()
                for (request in requestDataList) adapter.addItem(request)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

