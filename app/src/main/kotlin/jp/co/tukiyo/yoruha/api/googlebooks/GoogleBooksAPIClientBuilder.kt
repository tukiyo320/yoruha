package jp.co.tukiyo.yoruha.api.googlebooks

import android.content.Context
import jp.co.tukiyo.yoruha.api.AbstractAPIClientBuilder

class GoogleBooksAPIClientBuilder(context: Context) : AbstractAPIClientBuilder<GoogleBooksAPIClient>(
        context,
        "https://www.googleapis.com/",
        GoogleBooksAPIClient::class.java
)