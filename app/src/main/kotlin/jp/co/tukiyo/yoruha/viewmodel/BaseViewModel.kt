package jp.co.tukiyo.yoruha.viewmodel

import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.yoruha.application.Application

abstract class BaseViewModel : ViewModel {
    override val disposables: CompositeDisposable = CompositeDisposable()
    val application: Application
        get() = Application.from(context)
}
