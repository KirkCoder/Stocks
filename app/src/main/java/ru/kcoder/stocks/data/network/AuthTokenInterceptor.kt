package ru.kcoder.stocks.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.kcoder.stocks.config.TokenSource
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val tokenSource: TokenSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenSource.getToken()

        return sendWithToken(chain, token)
    }

    private fun sendWithToken(chain: Interceptor.Chain, token: String): Response {
        return chain.request().newBuilder()
            .addHeader(HEADER_AUTH_TOKEN, token)
            .build()
            .let(chain::proceed)
    }

    companion object {
        const val HEADER_AUTH_TOKEN = "Authorization"
    }
}