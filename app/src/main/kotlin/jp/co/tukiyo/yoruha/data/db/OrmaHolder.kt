package jp.co.tukiyo.yoruha.data.db

import android.content.Context

object OrmaHolder {
    lateinit var ORMA: OrmaDatabase

    fun init(context: Context) {
        ORMA = OrmaDatabase.builder(context).build()
    }
}