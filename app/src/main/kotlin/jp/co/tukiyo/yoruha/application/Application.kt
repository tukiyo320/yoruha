package jp.co.tukiyo.yoruha.application

import android.content.Context
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    companion object {
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }
    }
}