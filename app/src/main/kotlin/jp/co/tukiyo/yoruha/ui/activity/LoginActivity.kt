package jp.co.tukiyo.yoruha.ui.activity

import android.accounts.Account
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.facebook.android.crypto.keychain.AndroidConceal
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain
import com.facebook.crypto.CryptoConfig
import com.facebook.crypto.Entity
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.ActivityLoginBinding
import jp.co.tukiyo.yoruha.extensions.*
import java.io.BufferedOutputStream
import java.lang.Exception

class LoginActivity : BaseActivity<ActivityLoginBinding>(), GoogleApiClient.OnConnectionFailedListener {
    override val layoutResourceId: Int = R.layout.activity_login
    private val RC_SIGN_IN: Int = 10000

    val prefs: SharedPreferences by lazy { getSharedPreference() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.signInButton.apply {
            setSize(SignInButton.SIZE_STANDARD)
            setOnClickListener {
                signIn()
            }
        }
    }

    fun signIn() {
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                Auth.GoogleSignInApi.getSignInResultFromIntent(data).let {
                    handleSignInResult(it)
                }
            }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (!result.isSuccess) return

        val account = result.signInAccount
        Observable.create<String> { subscriber ->
            try {
                GoogleAuthUtil.getToken(this, Account(account.email, "com.google"), "oauth2:profile email").let {
                    subscriber.onNext(it)
                }
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
                .async(Schedulers.newThread())
                .onNext {
                    prefs.edit()
                            .putGoogleOAuthToken(it)
                            .apply()
                    startActivity(Intent(this, ScreenActivity::class.java))
                    finish()
                }
                .onError {
                    Toast.makeText(this, "failed get google account OAuth token", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }

    fun storeToken(token: String) {
        prefs.edit().putGoogleOAuthToken(crypto.encrypt(token)).apply()
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {

    }
}
