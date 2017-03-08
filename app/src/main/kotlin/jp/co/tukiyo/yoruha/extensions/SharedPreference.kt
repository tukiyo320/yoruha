package jp.co.tukiyo.yoruha.extensions

import android.content.SharedPreferences

fun SharedPreferences.getUuid(): String {
    return getString("uuid", "")
}

fun SharedPreferences.Editor.putUuid(uuid: String): SharedPreferences.Editor {
    return putString("uuid", uuid)
}

fun SharedPreferences.isAcceptedToPolicy(): Boolean {
    return getBoolean("acceptedToPolicy", false)
}

fun SharedPreferences.Editor.putAcceptedToPolicy(accepted: Boolean): SharedPreferences.Editor {
    return putBoolean("acceptedToPolicy", accepted)
}

fun SharedPreferences.getGoogleOAuthToken(): String {
    return getString("googleOAuthToken", "")
}

fun SharedPreferences.Editor.putGoogleOAuthToken(token: String): SharedPreferences.Editor {
    return putString("googleOAuthToken", token)
}

fun SharedPreferences.Editor.removeGoogleOAuthToken(): SharedPreferences.Editor {
    return remove("googleOAuthToken")
}

fun SharedPreferences.getUserEmail(): String {
    return getString("email", "")
}

fun SharedPreferences.Editor.putUserEmail(email: String): SharedPreferences.Editor {
    return putString("email", email)
}

fun SharedPreferences.Editor.removeUserEmail(): SharedPreferences.Editor {
    return remove("email")
}