package jp.co.tukiyo.yoruha.api.googlebooks

import jp.co.tukiyo.yoruha.api.AbstractAPIClientBuilder

class GoogleBooksAPIClientBuilder : AbstractAPIClientBuilder<GoogleBooksAPIClient>(
        "https://www.googleapis.com/",
        GoogleBooksAPIClient::class.java
)