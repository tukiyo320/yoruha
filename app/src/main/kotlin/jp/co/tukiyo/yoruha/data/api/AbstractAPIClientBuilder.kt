package jp.co.tukiyo.yoruha.data.api

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

open class AbstractAPIClientBuilder<T>(context: Context, private val baseUrl: String, private val clazz: Class<T>) {
    val moshi: Moshi = Moshi.Builder().build()

    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        chain.proceed(chain.request()).newBuilder()
                .header("Cache-Control", "public, max-age=" + (24 * 60 * 60))
                .build()
    }
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
            .build()

    fun build(): T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }
}