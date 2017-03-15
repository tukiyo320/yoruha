package jp.co.tukiyo.yoruha.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.yoruha.viewmodel.ActivityViewModel
import jp.co.tukiyo.yoruha.viewmodel.ViewModel

abstract class BaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutResourceId: Int
    lateinit var binding: BINDING
    var disposables: CompositeDisposable? = null
    private var bindingViewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.CREATE)
    }

    override fun onStart() {
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.START)
        super.onStart()
    }

    override fun onPause() {
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    override fun onResume() {
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.RESUME)
        super.onResume()
    }

    override fun onStop() {
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        disposables?.dispose()
        (bindingViewModel as? ActivityViewModel)?.lifeCycleEvent?.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    fun bindViewModel(viewModel: ActivityViewModel) {
        bindingViewModel = viewModel
    }
}
