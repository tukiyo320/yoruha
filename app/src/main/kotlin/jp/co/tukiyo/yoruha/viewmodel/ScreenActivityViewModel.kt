package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import jp.co.tukiyo.yoruha.element.UserInfo
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase
import jp.co.tukiyo.yoruha.usecase.UserAccountUseCase
import jp.keita.kagurazaka.rxproperty.RxProperty

class ScreenActivityViewModel(context: Context): ActivityViewModel(context) {
    private val accountUseCase = UserAccountUseCase(context)
    private val shelfUseCase = BookShelfManageUseCase(context)
    val userInfo: RxProperty<UserInfo> = RxProperty()
    val totalReadPage: RxProperty<Int> = RxProperty(0)

    fun applyUserInfo() {
        accountUseCase.getUserInfo()
                .onSuccess {
                    userInfo.set(it)
                }
                .subscribe()
    }

    fun applyTotalReadPage() {
        shelfUseCase.getPageOfAllHaveReadBooks()
                .compose(bindToLifecycle())
                .onSuccess {
                    totalReadPage.set(it)
                }
                .onError {  }
                .subscribe()
    }
}