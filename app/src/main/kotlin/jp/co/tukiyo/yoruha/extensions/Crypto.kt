package jp.co.tukiyo.yoruha.extensions

import android.util.Base64
import com.facebook.crypto.Crypto
import com.facebook.crypto.Entity

private val Crypto.ENCRYPT_KEY: String
    get() = "#TAGA"

fun Crypto.encrypt(plainText: String): String {
    return encrypt(plainText.toByteArray(Charsets.UTF_8), Entity.create(ENCRYPT_KEY)).let {
        Base64.encodeToString(it, Base64.DEFAULT)
    }
}

fun Crypto.decrypt(encrypted: String): String {
    return Base64.decode(encrypted, Base64.DEFAULT).let {
        String(decrypt(it, Entity.create(ENCRYPT_KEY)))
    }
}