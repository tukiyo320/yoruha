package jp.co.tukiyo.yoruha.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import jp.co.tukiyo.yoruha.element.BookShelf
import jp.co.tukiyo.yoruha.ui.fragment.BookShelfFragmentBuilder
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class BookShelfPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val tabs: List<BookShelf> = BookShelfManageUseCase.preShelves

    override fun getItem(position: Int): Fragment {
        return BookShelfFragmentBuilder(tabs[position]).build()
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabs[position].title
    }
}