package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.yoruha.ui.fragment.SearchResultListFragmentBuilder

class SearchResultListScreen(val query: String) : Screen {
    override val identify: String = SearchResultListScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> SearchResultListFragmentBuilder(query).build() }
}