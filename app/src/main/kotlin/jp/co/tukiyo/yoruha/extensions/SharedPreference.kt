package jp.co.tukiyo.yoruha.extensions

import android.content.SharedPreferences

fun SharedPreferences.getUuid() : String {
    return getString("uuid", "")
}

fun SharedPreferences.Editor.putUuid(uuid: String) : SharedPreferences.Editor {
    return putString("uuid", uuid)
}

fun SharedPreferences.isAcceptedToPolicy() : Boolean {
    return getBoolean("acceptedToPolicy", false)
}

fun SharedPreferences.Editor.putAcceptecToPolicy(accepted: Boolean) : SharedPreferences.Editor {
    return putBoolean("acceptecToPolicy", accepted)
}