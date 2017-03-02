package jp.co.tukiyo.yoruha.api

import okhttp3.OkHttpClient

object HttpClient {
    val instance = OkHttpClient()
}