package ru.kcoder.stocks.config

import javax.inject.Inject

class ApiUrlProvider @Inject constructor() {

    fun getUrlForConfig(apiUrlConfig: ApiUrlConfig): String {
        return apiUrlConfig.provideApi()
    }

    fun getStreamUrlForConfig(apiUrlConfig: ApiUrlConfig): String {
        return apiUrlConfig.provideStreamUrl()
    }
}