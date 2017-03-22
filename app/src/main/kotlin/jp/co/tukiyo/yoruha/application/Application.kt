package jp.co.tukiyo.yoruha.application

import android.content.Context
import jp.co.tukiyo.yoruha.data.db.OrmaHolder
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    companion object {
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }
    }

    override fun onCreate() {
        super.onCreate()
        
        OrmaHolder.init(this)
    }
}