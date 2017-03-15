package jp.co.tukiyo.yoruha.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.common.SignInButton
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentLoginBinding
import jp.co.tukiyo.yoruha.viewmodel.LoginFragmentViewModel

@FragmentWithArgs
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    @Arg
    var logout: Boolean = false

    override val layoutResourceId: Int = R.layout.fragment_login
    private val loginViewModel by lazy {
        LoginFragmentViewModel(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(loginViewModel)
        if (logout) {
            loginViewModel.logout()
        } else {
            loginViewModel.login(this)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signInButton?.apply {
            setSize(SignInButton.SIZE_STANDARD)
            setOnClickListener {
                loginViewModel.signIn(this@LoginFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginViewModel.onSignInActivityResult(this, requestCode, resultCode, data)
    }
}