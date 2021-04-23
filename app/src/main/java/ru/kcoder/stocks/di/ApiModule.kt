package ru.kcoder.stocks.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kcoder.stocks.config.ApiUrlConfig
import ru.kcoder.stocks.config.ApiUrlProvider
import ru.kcoder.stocks.data.network.AuthTokenInterceptor
import ru.kcoder.stocks.data.network.LoggingInterceptor
import ru.kcoder.stocks.data.network.MetaHeadersInterceptor
import ru.kcoder.stocks.data.network.StocksApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun stocksApi(retrofit: Retrofit): StocksApi {
        return retrofit.create(StocksApi::class.java)
    }

    @Provides
    fun okHttpClient(
        loggingInterceptor: LoggingInterceptor,
        authTokenInterceptor: AuthTokenInterceptor,
        metaHeadersInterceptor: MetaHeadersInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(authTokenInterceptor)
            .addInterceptor(metaHeadersInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        apiUrlProvider: ApiUrlProvider,
        apiUrlConfig: ApiUrlConfig
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.getUrlForConfig(apiUrlConfig))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun streamRequest(
        apiUrlProvider: ApiUrlProvider,
        apiUrlConfig: ApiUrlConfig
    ): Request {
        return Request.Builder().url(apiUrlProvider.getStreamUrlForConfig(apiUrlConfig)).build()
    }

    companion object {
        private const val TIMEOUT_SECONDS = 60L
    }
}