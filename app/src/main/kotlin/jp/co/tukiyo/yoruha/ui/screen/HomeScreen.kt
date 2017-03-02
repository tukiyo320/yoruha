package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.yoruha.ui.fragment.HomeFragmentBuilder

class HomeScreen : Screen{
    override val identify: String = HomeScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> HomeFragmentBuilder().build() }
}