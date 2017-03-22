package jp.co.tukiyo.yoruha.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.tukiyo.yoruha.data.db.OrmaHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OrmaHolder.init(this)

        startActivity(Intent(this, ScreenActivity::class.java))
        finish()
    }

}
