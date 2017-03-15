package jp.co.tukiyo.yoruha.usecase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.element.UserInfo
import jp.co.tukiyo.yoruha.extensions.*
import jp.co.tukiyo.yoruha.repository.GoogleBooksRepository

class UserAccountUseCase(context: Context) {
    private val repository = GoogleBooksRepository(context)
    private val prefs = context.getSharedPreference()

    fun login(): Completable {
        val email = prefs.getUserEmail()
        return if (email.isNotEmpty()) {
            repository.authorize(email)
                    .doOnError {
                        removeUserInfo()
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
                            .putUserDisplayName(account.displayName)
                            .putUserPhotoUrl(account.photoUrl.toString())
                            .apply()
                }
                .doOnError {
                    removeUserInfo()
                }
    }

    fun getUserInfo(): Single<UserInfo> {
        val userInfo = UserInfo(prefs.getUserEmail(), prefs.getUserDisplayName(), prefs.getUserPhotoUrl())
        return Single.just(userInfo).async(Schedulers.newThread())
    }

    fun removeUserInfo() {
        prefs.edit()
                .removeUserEmail()
                .removeUserDisplayName()
                .removeUserPhotoUrl()
                .apply()
    }

    fun isAcceptedToPolicy(): Boolean = prefs.isAcceptedToPolicy()
}