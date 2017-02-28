package jp.co.tukiyo.yoruha.ui.screen

import android.support.v4.app.Fragment

interface Screen {
    val identify: String
    val fragment: Fragment
        get() = fragmentFactory.invoke()
    val fragmentFactory: () -> Fragment
}
