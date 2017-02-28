package jp.co.tukiyo.yoruha.ui.activity

import android.os.Bundle
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.ActivityScreenBinding
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUuid
import jp.co.tukiyo.yoruha.extensions.putUuid
import jp.co.tukiyo.yoruha.ui.screen.Screen
import java.util.*

class ScreenActivity : BaseActivity<ActivityScreenBinding>() {
    override val layoutResourceId: Int = R.layout.activity_screen

    fun pushScreen(screen: Screen) {
        if (supportFragmentManager.findFragmentByTag(screen.identify) != null) return

        supportFragmentManager.beginTransaction()
                .replace(R.id.screen, screen.fragment, screen.identify)
                .addToBackStack(screen.identify)
                .commit()
        supportFragmentManager.executePendingTransactions()
    }

    fun popScreen() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStackImmediate()
    }

    fun replaceScreen(screen: Screen) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.screen, screen.fragment, screen.identify)
                .commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkUuid()
    }

    fun checkUuid() {
        getSharedPreference().run {
            if (getUuid().isEmpty()) {
                edit().putUuid(UUID.randomUUID().toString()).apply()
            }
        }
    }
}