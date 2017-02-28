package jp.co.tukiyo.yoruha.extensions

import android.content.SharedPreferences

fun SharedPreferences.getUuid() : String {
    return getString("uuid", "")
}

fun SharedPreferences.Editor.putUuid(uuid: String) : SharedPreferences.Editor {
    return putString("uuid", uuid)
}