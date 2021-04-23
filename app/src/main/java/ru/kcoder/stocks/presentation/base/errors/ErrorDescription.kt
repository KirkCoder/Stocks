package ru.kcoder.stocks.presentation.base.errors

import ru.kcoder.stocks.R
import ru.kcoder.stocks.data.network.StocksError
import ru.kcoder.stocks.presentation.base.ResourceDataStore
import timber.log.Timber
import javax.inject.Inject

class ErrorDescription(
    val description: String
) {
    class Formatter @Inject constructor(
        private val resourceDataStore: ResourceDataStore,
    ) {
        fun format(error: Throwable): ErrorDescription {
            val message = getErrorMessage(error)
            return ErrorDescription(
                description = message
            )
        }

        private fun getErrorMessage(error: Throwable): String {
            return if (error is StocksError) {
                when (error) {
                    is StocksError.Server -> {
                        error.message ?: getCommonErrorDescription()
                    }
                    is StocksError.StreamConnection -> {
                        error.outError?.let(Timber::e)
                        resourceDataStore.getString(R.string.connection_lost)
                    }
                }
            } else {
                getCommonErrorDescription()
            }
        }

        private fun getCommonErrorDescription() =
            resourceDataStore.getString(R.string.sorry_something_went_wrong)
    }
}