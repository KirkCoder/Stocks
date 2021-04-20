package ru.kcoder.stocks.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MetaHeadersInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_CONTENT)
            .addHeader(HEADER_ACCEPT_LANGUAGE, HEADER_ACCEPT_LANGUAGE_CONTENT)
            .build()
            .let(chain::proceed)
    }

    companion object {
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_ACCEPT_CONTENT = "application/json"
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
        const val HEADER_ACCEPT_LANGUAGE_CONTENT = "nl-NL,en;q=0.8"
    }
}