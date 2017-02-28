package jp.co.tukiyo.yoruha.ui.activity

import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.ActivityScreenBinding
import jp.co.tukiyo.yoruha.ui.screen.Screen

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
}