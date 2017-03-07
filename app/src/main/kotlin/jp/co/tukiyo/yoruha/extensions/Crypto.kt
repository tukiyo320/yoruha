package jp.co.tukiyo.yoruha.extensions

import com.facebook.crypto.Crypto
import com.facebook.crypto.Entity

private val Crypto.ENCRYPT_KEY : String
        get() = "#TAGA"

fun Crypto.encrypt(planText: String) : String {
    return String(encrypt(planText.toByteArray(Charsets.UTF_8), Entity.create(ENCRYPT_KEY)), Charsets.UTF_8)
}

fun Crypto.decrypt(encrypted: String) : String {
    return String(decrypt(encrypted.toByteArray(Charsets.UTF_8), Entity.create(ENCRYPT_KEY)), Charsets.UTF_8)
}