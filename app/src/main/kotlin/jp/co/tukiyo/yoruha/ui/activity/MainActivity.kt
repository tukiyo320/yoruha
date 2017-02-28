package jp.co.tukiyo.yoruha.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUuid
import jp.co.tukiyo.yoruha.extensions.putUuid
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreference().run {
            if (getUuid().isEmpty()) {
                edit()
                    .putUuid(UUID.randomUUID().toString())
                    .apply()
            }
        }

        startActivity(Intent(this, ScreenActivity::class.java))
    }

}
