package com.socu.enpit.sss

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nifcloud.mbaas.core.*
import java.io.ByteArrayOutputStream

object CloudDataManager {
    // 全店舗共通のクラス
    private const val CLASS_SHARE_SHOP_INFO = "ShopInfo"

    // 店舗ごとに存在するクラス
    private const val CLASS_EACH_NEWS = "_News"
    private const val CLASS_EACH_MENU = "_Menu"
    private const val CLASS_EACH_GROCERIES = "_Groceries"
    private const val CLASS_EACH_REQUEST = "_Request"

    // データの列
    //private const val KEY_CREATE_DATE: String = "createDate" // ニフクラ側が勝手に作る時刻。もう使わないかも。
    private const val KEY_MY_CREATE_DATE: String = "myCreateDate"
    private const val KEY_USER_NAME: String = "userName"
    private const val KEY_SHOP_NAME: String = "shopName"
    private const val KEY_TITLE: String = "title"
    private const val KEY_CONTENTS: String = "contents"
    private const val KEY_IMAGE_NAME: String = "imageName"

    // 全店舗リクエスト用アカウント名
    private const val DEFAULT_ACCOUNT_USER_NAME = "_everyone"

    // 画像ファイル名
    // お店画像。アカウント名の後ろにつける。
    private const val IMAGE_NAME = "_ShopImage.png"

    private var accountUserName: String? = null

    private class KeyAndData constructor(_key: String, _data:String) {
        val key: String = _key
        val data: String = _data
    }

    fun setAccountUserName(name: String) {
        accountUserName = name
    }
    fun setAccountUserNameDefault() {
        accountUserName = DEFAULT_ACCOUNT_USER_NAME
    }
    fun getAccountUserNameDefault(): String {
        return DEFAULT_ACCOUNT_USER_NAME
    }
    fun setAccountUserNameFromShopName(shopName: String): Boolean {
        val findKads = listOf(KeyAndData(KEY_SHOP_NAME, shopName))
        val ret = getStringData(CLASS_SHARE_SHOP_INFO, findKads, KEY_USER_NAME)
        if (ret != null)
        {
            accountUserName = ret
            return true
        }
        return false
    }
    private fun getClassEachName(className: String): String {
        return accountUserName!! + className
    }
    private fun getStringDataList(className: String): List<NCMBObject> {
        val query = NCMBQuery<NCMBObject>(className)
        query.addOrderByAscending(KEY_MY_CREATE_DATE)
        try {
            return query.find()
        } catch (e: NCMBException) {
            // エラー処理
        }
        return ArrayList()
    }
    private fun getStringData(className: String, findKads: List<KeyAndData>, getKey: String): String? {
        val query = NCMBQuery<NCMBObject>(className)
        for (kad in findKads) query.whereEqualTo(kad.key, kad.data)
        query.setLimit(1)
        try {
            val list: List<NCMBObject> = query.find()
            if (list.isEmpty()) {
                return null
            }
            val obj = list[0]
            return obj.getString(getKey)
        } catch (e: NCMBException) {
            // エラー処理
        }
        return null
    }
    private fun addStringData(className: String, insertKads: List<KeyAndData>) {
        val obj = NCMBObject(className)
        for (kad in insertKads)  obj.put(kad.key, kad.data)
        obj.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
    }
    private fun overwriteStringData(className: String, findKads: List<KeyAndData>, insertKads: List<KeyAndData>): Boolean {
        val query = NCMBQuery<NCMBObject>(className)
        for (kad in findKads) query.whereEqualTo(kad.key, kad.data)
        query.setLimit(1)
        try {
            val list: List<NCMBObject> = query.find()
            if (list.isEmpty()) {
                return false
            }
            val obj = list[0]
            for (kad in insertKads) obj.put(kad.key, kad.data)
            obj.saveInBackground { e ->
                if (e != null) {
                    // 保存に失敗した場合の処理
                } else {
                    // 保存に成功した場合の処理
                }
            }
            return true
        } catch (e: NCMBException) {
            // エラー処理
        }
        return false
    }
    fun setShopName(name: String) {
        val findKads = listOf(KeyAndData(KEY_USER_NAME, accountUserName!!))
        val insertKads = listOf(KeyAndData(KEY_SHOP_NAME, name))
        val success = overwriteStringData(CLASS_SHARE_SHOP_INFO, findKads, insertKads)
        if (!success) {
            val kads = arrayListOf(findKads, insertKads).flatten()
            addStringData(CLASS_SHARE_SHOP_INFO, kads)
        }
    }
    fun getShopName(): String {
        val findKads = listOf(KeyAndData(KEY_USER_NAME, accountUserName!!))
        val ret = getStringData(CLASS_SHARE_SHOP_INFO, findKads, KEY_SHOP_NAME)
        if (ret != null) return ret
        val insertKads = listOf(KeyAndData(KEY_SHOP_NAME, accountUserName!!))
        val kads = arrayListOf(findKads, insertKads).flatten()
        addStringData(CLASS_SHARE_SHOP_INFO, kads)
        return accountUserName!!
    }
    fun setShopImage(bmp: Bitmap) {
        val byteArrayStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 0, byteArrayStream)
        val dataByte = byteArrayStream.toByteArray()
        val acl = NCMBAcl()
        acl.publicReadAccess = true
        acl.publicWriteAccess = true
        val imageName = accountUserName!! + IMAGE_NAME
        val file = NCMBFile(imageName, dataByte, acl)
        file.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
    }
    fun getShopImage() : Bitmap? {
        try {
            val imageName = accountUserName!! + IMAGE_NAME
            val file = NCMBFile(imageName)
            val dataFetch = file.fetch()
            return BitmapFactory.decodeByteArray(dataFetch, 0, dataFetch.size)
        } catch (e: NCMBException) {

        }
        return null
    }
    fun addNewsData(data: NewsData) {
        val kads = listOf(KeyAndData(KEY_TITLE, data.title), KeyAndData(KEY_CONTENTS, data.contents), KeyAndData(KEY_MY_CREATE_DATE, data.date))
        val className = getClassEachName(CLASS_EACH_NEWS)
        addStringData(className, kads)
    }
    fun getNewsDataList(): List<NewsData> {
        val className = getClassEachName(CLASS_EACH_NEWS)
        val list = getStringDataList(className)
        val ret = arrayListOf<NewsData>()
        for(data in list) ret.add(NewsData(data.getString(KEY_TITLE), data.getString(KEY_CONTENTS), data.getString(KEY_MY_CREATE_DATE)))
        return ret
    }
    fun addMenuData(data: MenuData) {
        val kads = listOf(KeyAndData(KEY_TITLE, data.title), KeyAndData(KEY_CONTENTS, data.contents), KeyAndData(KEY_MY_CREATE_DATE, data.date))
        val className = getClassEachName(CLASS_EACH_MENU)
        addStringData(className, kads)
    }
    fun getMenuDataList(): List<MenuData> {
        val className = getClassEachName(CLASS_EACH_MENU)
        val list = getStringDataList(className)
        val ret = arrayListOf<MenuData>()
        for(data in list) ret.add(MenuData(data.getString(KEY_TITLE), data.getString(KEY_CONTENTS), data.getString(KEY_MY_CREATE_DATE)))
        return ret
    }
    fun addRequestData(data: RequestData) {
        val kads = listOf(KeyAndData(KEY_TITLE, data.title), KeyAndData(KEY_CONTENTS, data.contents), KeyAndData(KEY_MY_CREATE_DATE, data.date))
        val className = getClassEachName(CLASS_EACH_REQUEST)
        addStringData(className, kads)
    }
    fun getRequestDataList(): List<RequestData> {
        val className = getClassEachName(CLASS_EACH_REQUEST)
        val list = getStringDataList(className)
        val ret = arrayListOf<RequestData>()
        for(data in list) ret.add(RequestData(data.getString(KEY_TITLE), data.getString(KEY_CONTENTS), data.getString(KEY_MY_CREATE_DATE)))
        return ret
    }//    fun addGroceryData(data: GroceryData) {
//        val byteArrayStream = ByteArrayOutputStream()
//        data.bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayStream)
//        val dataByte = byteArrayStream.toByteArray()
//        val acl = NCMBAcl()
//        acl.publicReadAccess = true
//        acl.publicWriteAccess = true
//        val file = NCMBFile(data.imageName, dataByte, acl)
//        file.saveInBackground { e ->
//            if (e != null) {
//                // 保存に失敗した場合の処理
//            } else {
//                // 保存に成功した場合の処理
//            }
//        }
//    }
//    fun getGroceryDataList(): List<GroceryData> {
//        val className = getClassEachName(CLASS_EACH_GROCERIES)
//        val list = getStringDataList(className)
//        val ret = arrayListOf<GroceryData>()
//        for(data in list) {
//            val imageName = data.getString(KEY_IMAGE_NAME)
//            val file = NCMBFile(imageName)
//            val dataFetch = file.fetch()
//            val bitmap = BitmapFactory.decodeByteArray(dataFetch, 0, dataFetch.size)
//            ret.add(GroceryData(data.getString(KEY_TITLE), data.getString(KEY_CONTENTS), imageName, bitmap, data.getString(KEY_CREATE_DATE)))
//        }
//        return ret
//    }

    fun getShopDataList(): List<ShopData> {
        val list = getStringDataList(CLASS_SHARE_SHOP_INFO)
        val ret = arrayListOf<ShopData>()
        for(data in list) ret.add(ShopData(data.getString(KEY_SHOP_NAME)))
        return ret
    }
}