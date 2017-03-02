package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.yoruha.ui.fragment.PolicyFragmentBuilder

class PolicyScreen : Screen {
    override val identify: String = PolicyScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> PolicyFragmentBuilder().build()}
}