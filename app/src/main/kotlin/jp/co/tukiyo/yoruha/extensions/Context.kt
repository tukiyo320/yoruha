package jp.co.tukiyo.yoruha.extensions

import android.content.Context
import android.content.SharedPreferences
import com.facebook.android.crypto.keychain.AndroidConceal
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain
import com.facebook.crypto.Crypto
import com.facebook.crypto.CryptoConfig

val Context.crypto : Crypto
    get() {
        val keyChain = SharedPrefsBackedKeyChain(this, CryptoConfig.KEY_256)
        val crypto = AndroidConceal.get().createDefaultCrypto(keyChain)
        if (!crypto.isAvailable) {
            throw Exception("Cannot use Conceal!!")
        }
        return crypto
    }

fun Context.getSharedPreference() : SharedPreferences {
    return this.getSharedPreferences("jp.co.tukiyo.yoruha", Context.MODE_PRIVATE)
}
