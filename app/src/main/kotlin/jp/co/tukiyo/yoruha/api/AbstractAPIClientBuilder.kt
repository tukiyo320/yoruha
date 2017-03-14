package jp.co.tukiyo.yoruha.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

open class AbstractAPIClientBuilder<T>(private val baseUrl: String, private val clazz: Class<T>) {
    val moshi : Moshi = Moshi.Builder().build()

    fun build(): T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }
}