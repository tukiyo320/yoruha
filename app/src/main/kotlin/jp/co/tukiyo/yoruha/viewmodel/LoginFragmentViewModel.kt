package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
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
    private val googleApiClient: GoogleApiClient = getGoogleAPIClient(context)

    fun getGoogleAPIClient(context: Context): GoogleApiClient {
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        return GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    fun login(fragment: BaseFragment<*>) {
        accountUseCase.login()
                .compose(bindToLifecycle<CompletableTransformer>())
                .onCompleted {
                    fragment.screenActivity.run {
                        applyUserInfo()
                        replaceScreen(getNextScreen())
                    }
                }
                .onError { }
                .subscribe()
    }

    fun logout() {
        googleApiClient.registerConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
            override fun onConnected(p0: Bundle?) {
                if (googleApiClient.isConnected) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
                        accountUseCase.logout()
                                .compose(bindToLifecycle<CompletableTransformer>())
                                .subscribe()
                    }
                }
            }

            override fun onConnectionSuspended(p0: Int) {

            }

        })
        googleApiClient.connect()
    }

    fun signIn(fragment: BaseFragment<*>) {
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
                    fragment.screenActivity.run {
                        applyUserInfo()
                        replaceScreen(getNextScreen())
                    }
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
