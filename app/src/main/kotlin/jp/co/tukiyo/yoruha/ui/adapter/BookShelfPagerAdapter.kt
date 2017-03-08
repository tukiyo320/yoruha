package jp.co.tukiyo.yoruha.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.astuetz.PagerSlidingTabStrip
import jp.co.tukiyo.yoruha.ui.fragment.BookShelfFragmentBuilder
import java.io.Serializable

class BookShelfPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val tabs: MutableList<BookShelf> = mutableListOf(BookShelf(3, "読んでる本", false), BookShelf(2, "読みたい本", false), BookShelf(4, "読んだ本", false))
    lateinit var view: PagerSlidingTabStrip

    override fun getItem(position: Int): Fragment {
        return BookShelfFragmentBuilder(tabs[position]).build()
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabs[position].title
    }

    fun addTab(info: BookShelf) {
        tabs.add(info)
        notifyDataSetChanged()
        view.notifyDataSetChanged()
    }

    fun removeTab(position: Int) {
        tabs.removeAt(position)
        notifyDataSetChanged()
        view.notifyDataSetChanged()
    }

    fun getItemInfo(position: Int): BookShelf {
        return tabs[position]
    }

    data class BookShelf(
            val no: Int,
            val title: String,
            val removable: Boolean
    ): Serializable
}