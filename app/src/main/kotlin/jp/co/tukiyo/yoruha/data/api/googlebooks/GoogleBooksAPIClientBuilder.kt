package jp.co.tukiyo.yoruha.data.api.googlebooks

import android.content.Context
import jp.co.tukiyo.yoruha.data.api.AbstractAPIClientBuilder

class GoogleBooksAPIClientBuilder(context: Context) : AbstractAPIClientBuilder<GoogleBooksAPIClient>(
        context,
        "https://www.googleapis.com/",
        GoogleBooksAPIClient::class.java
)