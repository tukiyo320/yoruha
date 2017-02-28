package jp.co.tukiyo.yoruha.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreference()
        if (prefs.getString("uuid", "").isEmpty()) {
            prefs.edit()
                    .putString("uuid", UUID.randomUUID().toString())
                    .apply()
        }

        startActivity(Intent(this, ScreenActivity::class.java))
    }

}
