package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.CompletableTransformer
import jp.co.tukiyo.yoruha.extensions.onCompleted
import jp.co.tukiyo.yoruha.ui.fragment.BaseFragment
import jp.co.tukiyo.yoruha.ui.screen.HomeScreen
import jp.co.tukiyo.yoruha.ui.screen.PolicyScreen
import jp.co.tukiyo.yoruha.ui.screen.Screen
import jp.co.tukiyo.yoruha.usecase.UserAccountUseCase

class LoginFragmentViewModel(context: Context) : FragmentViewModel(context), GoogleApiClient.OnConnectionFailedListener {
    private val accountUseCase = UserAccountUseCase(context)
    private val RC_SIGN_IN = 10000

    fun login(fragment: BaseFragment<*>) {
        accountUseCase.login()
                .compose(bindToLifecycle<CompletableTransformer>())
                .onCompleted {
                    fragment.screenActivity.replaceScreen(getNextScreen())
                }
                .onError { }
                .subscribe()
    }

    fun signIn(fragment: BaseFragment<*>) {
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val googleApiClient = GoogleApiClient.Builder(fragment.context)
                .enableAutoManage(fragment.activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        fragment.startActivityForResult(intent, RC_SIGN_IN)
    }

    fun onSignInActivityResult(fragment: BaseFragment<*>, requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> {
                Auth.GoogleSignInApi.getSignInResultFromIntent(data).let {
                    handleSignInResult(fragment, it)
                }
            }
        }
    }

    private fun handleSignInResult(fragment: BaseFragment<*>, result: GoogleSignInResult) {
        if (!result.isSuccess) return

        val account = result.signInAccount

        accountUseCase.authorize(account)
                .compose(bindToLifecycle<CompletableTransformer>())
                .onCompleted {
                    fragment.screenActivity.replaceScreen(getNextScreen())
                }
                .onError { e ->
                    if (e is UserRecoverableAuthException) {
                        fragment.startActivityForResult(e.intent, RC_SIGN_IN)
                    }
                }
                .subscribe()
    }

    private fun getNextScreen(): Screen {
        return if (accountUseCase.isAcceptedToPolicy()) {
            HomeScreen()
        } else PolicyScreen()
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {

    }
}
