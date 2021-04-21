package ru.kcoder.stocks.config

import javax.inject.Inject

class ApiUrlConfig @Inject constructor() {

    fun provideApi(): String {
        return MAIN_API_URL
    }

    fun provideStreamUrl(): String {
        return MAIN_STREAM_URL
    }

    companion object {
        private const val MAIN_API_URL = "https://api.beta.getbux.com/"
        private const val MAIN_STREAM_URL = "https://rtf.beta.getbux.com/subscriptions/me"
    }
}