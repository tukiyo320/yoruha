package jp.co.tukiyo.yoruha.usecase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import jp.co.tukiyo.yoruha.extensions.*
import jp.co.tukiyo.yoruha.repository.GoogleBooksRepository

class UserAccountUseCase(context: Context) {
    private val repository = GoogleBooksRepository(context)
    private val prefs = context.getSharedPreference()

    fun login(): Completable {
        val email = prefs.getUserEmail()
        return if (email.isNotEmpty()) {
            repository.authorize(email)
                    .doOnComplete {
                        prefs.edit()
                                .putUserEmail(email)
                                .apply()
                    }
        } else {
            Completable.error(Exception())
        }

    }

    fun authorize(account: GoogleSignInAccount): Completable {
        return repository.authorize(account.email)
                .doOnComplete {
                    prefs.edit()
                            .putUserEmail(account.email)
                            .apply()
                }
                .doOnError {
                    prefs.edit()
                            .removeUserEmail()
                            .apply()
                }
    }

    fun isAcceptedToPolicy(): Boolean = prefs.isAcceptedToPolicy()
}