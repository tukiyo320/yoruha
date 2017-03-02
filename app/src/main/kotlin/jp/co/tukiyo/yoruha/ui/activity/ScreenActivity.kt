package jp.co.tukiyo.yoruha.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.ActivityScreenBinding
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUuid
import jp.co.tukiyo.yoruha.extensions.isAcceptedToPolicy
import jp.co.tukiyo.yoruha.extensions.putUuid
import jp.co.tukiyo.yoruha.ui.screen.PolicyScreen
import jp.co.tukiyo.yoruha.ui.screen.Screen
import java.util.*

class ScreenActivity : BaseActivity<ActivityScreenBinding>() {
    override val layoutResourceId: Int = R.layout.activity_screen
    val prefs : SharedPreferences
        get() = getSharedPreference()

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

        if (!prefs.isAcceptedToPolicy()) {
            replaceScreen(PolicyScreen())
        }
    }

    fun checkUuid() {
        prefs.run {
            if (getUuid().isEmpty()) {
                edit().putUuid(UUID.randomUUID().toString()).apply()
            }
        }
    }
}