package jp.co.tukiyo.yoruha.ui.activity

import android.app.FragmentManager
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.ActivityScreenBinding
import jp.co.tukiyo.yoruha.databinding.NavigationHeaderBinding
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUuid
import jp.co.tukiyo.yoruha.extensions.isAcceptedToPolicy
import jp.co.tukiyo.yoruha.extensions.putUuid
import jp.co.tukiyo.yoruha.ui.screen.HomeScreen
import jp.co.tukiyo.yoruha.ui.screen.LoginScreen
import jp.co.tukiyo.yoruha.ui.screen.PolicyScreen
import jp.co.tukiyo.yoruha.ui.screen.Screen
import jp.co.tukiyo.yoruha.viewmodel.ScreenActivityViewModel
import java.util.*

class ScreenActivity : BaseActivity<ActivityScreenBinding>() {
    override val layoutResourceId: Int = R.layout.activity_screen
    val prefs: SharedPreferences by lazy {
        getSharedPreference()
    }
    val navigationHeaderBinding: NavigationHeaderBinding by lazy {
        DataBindingUtil.bind<NavigationHeaderBinding>(binding.navigationView.getHeaderView(0))
    }
    private val viewModel by lazy {
        ScreenActivityViewModel(this)
    }

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

    fun popAllScreen() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(viewModel)

        navigationHeaderBinding.viewModel = viewModel
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_logout -> {
                    popAllScreen()
                    replaceScreen(LoginScreen(true))
                }
            }
            return@setNavigationItemSelectedListener false
        }
        checkUuid()
        replaceScreen(LoginScreen(false))
    }

    fun applyUserInfo() {
        viewModel.applyUserInfo()
    }

    fun applyTotalReadPage() {
//        viewModel.applyTotalReadPage()
    }

    fun setDrawerLockMode(mode: Int) {
        binding.screenDrawer.setDrawerLockMode(mode)
    }

    fun checkUuid() {
        prefs.run {
            if (getUuid().isEmpty()) {
                edit().putUuid(UUID.randomUUID().toString()).apply()
            }
        }
    }
}