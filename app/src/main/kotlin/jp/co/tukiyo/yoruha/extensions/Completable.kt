package jp.co.tukiyo.yoruha.extensions

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun Completable.async(scheduler: Scheduler): Completable {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.sync(): Completable {
    return subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.onError(block: (Throwable) -> Unit): CompletableSubscription {
    return CompletableSubscription(this).onError(block)
}

fun Completable.onCompleted(block: () -> Unit): CompletableSubscription {
    return CompletableSubscription(this).onCompleted(block)
}

open class CompletableSubscription(val completable: Completable) {
    private var error: (Throwable) -> Unit = { throw it }
    private var completed: () -> Unit = {}

    fun onError(block: (Throwable) -> Unit): CompletableSubscription {
        error = block
        return this
    }

    fun onCompleted(block: () -> Unit): CompletableSubscription {
        completed = block
        return this
    }

    fun subscribe(): Disposable = completable.subscribe({
        completed.invoke()
    }, {
        error.invoke(it)
    })
}