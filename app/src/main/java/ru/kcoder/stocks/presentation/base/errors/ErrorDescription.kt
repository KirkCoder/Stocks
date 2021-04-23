package ru.kcoder.stocks.presentation.base.errors

import ru.kcoder.stocks.R
import ru.kcoder.stocks.data.network.ServerError
import ru.kcoder.stocks.presentation.base.ResourceDataStore
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
            val errorMessage = error.message
            return if (error is ServerError && errorMessage != null) {
                errorMessage
            } else {
                resourceDataStore.getString(R.string.sorry_something_went_wrong)
            }
        }
    }
}