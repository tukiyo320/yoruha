package jp.co.tukiyo.yoruha.extensions

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun <T> Single<T>.async(scheduler: Scheduler): Single<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.sync(): Single<T> {
    return subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.onError(block: (Throwable) -> Unit): SingleSubscription<T> {
    return SingleSubscription(this).onError(block)
}

fun <T> Single<T>.onSuccess(block: (T) -> Unit): SingleSubscription<T> {
    return SingleSubscription(this).onNext(block)
}

open class SingleSubscription<T>(val single: Single<T>) {
    private var error: (Throwable) -> Unit = { throw it }
    private var completed: () -> Unit = {}
    private var success: (T) -> Unit = {}

    fun onError(block: (Throwable) -> Unit): SingleSubscription<T> {
        error = block
        return this
    }

    fun onCompleted(block: () -> Unit): SingleSubscription<T> {
        completed = block
        return this
    }

    fun onNext(block: (T) -> Unit): SingleSubscription<T> {
        success = block
        return this
    }

    fun subscribe(): Disposable = single.subscribe({
        success.invoke(it)
    }, {
        it?.let { error.invoke(it) }
    })
}
