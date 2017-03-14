package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.yoruha.ui.fragment.LoginFragmentBuilder

class LoginScreen : Screen {
    override val identify: String = LoginScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> LoginFragmentBuilder().build() }
}