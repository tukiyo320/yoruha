package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.yoruha.ui.fragment.BookInfoFragmentBuilder

class BookInfoScreen(volumeId: String): Screen {
    override val identify: String = BookInfoScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> BookInfoFragmentBuilder(volumeId).build()}
}