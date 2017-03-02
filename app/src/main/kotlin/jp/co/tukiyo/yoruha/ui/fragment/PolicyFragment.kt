package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentPolicyBinding
import jp.co.tukiyo.yoruha.ui.screen.HomeScreen
import jp.co.tukiyo.yoruha.viewmodel.PolicyFragmentViewModel

@FragmentWithArgs
class PolicyFragment : BaseFragment<FragmentPolicyBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_policy
    val viewModel: PolicyFragmentViewModel
        get() = PolicyFragmentViewModel(context)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            policyWebView.loadUrl("http://seiga.nicovideo.jp/rule")
            policyAcceptButton.setOnClickListener {
                viewModel.acceptPolicy()
                screenActivity.replaceScreen(HomeScreen())
            }
        }
    }
}