package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import jp.co.tukiyo.yoruha.element.UserInfo
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.usecase.UserAccountUseCase
import jp.keita.kagurazaka.rxproperty.RxProperty

class ScreenActivityViewModel(context: Context): ActivityViewModel(context) {
    private val useCase = UserAccountUseCase(context)
    val userInfo: RxProperty<UserInfo> = RxProperty()

    fun applyUserInfo() {
        useCase.getUserInfo()
                .onSuccess {
                    userInfo.set(it)
                }
                .subscribe()
    }

}