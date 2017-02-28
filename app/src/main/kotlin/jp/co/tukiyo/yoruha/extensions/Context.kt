package jp.co.tukiyo.yoruha.extensions

import android.content.Context
import android.content.SharedPreferences

fun Context.getSharedPreference() : SharedPreferences {
    return this.getSharedPreferences("jp.co.tukiyo.yoruha", Context.MODE_PRIVATE)
}