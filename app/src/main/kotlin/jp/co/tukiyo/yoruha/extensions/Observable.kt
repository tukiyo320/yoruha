package jp.co.tukiyo.yoruha.extensions

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.async(scheduler: Scheduler): Observable<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.sync(): Observable<T> {
    return subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
}
fun <T> Observable<T>.onError(block : (Throwable) -> Unit) : ObservableSubscription<T> {
    return ObservableSubscription(this).onError(block)
}

fun <T> Observable<T>.onCompleted(block: () -> Unit) : ObservableSubscription<T> {
    return ObservableSubscription(this).onCompleted(block)
}

fun <T> Observable<T>.onNext(block: (T) -> Unit) : ObservableSubscription<T> {
    return ObservableSubscription(this).onNext(block)
}

open class ObservableSubscription<T>(val observable: Observable<T>) {
    private var error: (Throwable) -> Unit = {throw it}
    private var completed: () -> Unit = {}
    private var next: (T) -> Unit = {}

    fun onError(block: (Throwable) -> Unit) : ObservableSubscription<T> {
        error = block
        return this
    }

    fun onCompleted(block: () -> Unit) : ObservableSubscription<T> {
        completed = block
        return this
    }

    fun onNext(block: (T) -> Unit) : ObservableSubscription<T> {
        next = block
        return this
    }

    fun subscribe(): Disposable = observable.subscribe({
        next.invoke(it)
    },{
        it?.let {error.invoke(it)}
    },{
        completed.invoke()
    })
}
