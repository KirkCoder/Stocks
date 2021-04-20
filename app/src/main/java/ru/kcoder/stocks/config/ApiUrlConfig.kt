package ru.kcoder.stocks.config

import javax.inject.Inject

class ApiUrlConfig @Inject constructor() {

    fun provideApi(): String {
        return MAIN_API_URL
    }

    companion object {
        private const val MAIN_API_URL = "https://api.beta.getbux.com/"
    }
}