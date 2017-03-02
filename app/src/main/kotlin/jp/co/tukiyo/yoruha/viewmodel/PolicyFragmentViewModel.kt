package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.content.SharedPreferences
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.putAcceptecToPolicy

class PolicyFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val prefs : SharedPreferences
        get() = context.getSharedPreference()

    fun acceptPolicy() {
        prefs.edit().putAcceptecToPolicy(true).apply()
    }
}