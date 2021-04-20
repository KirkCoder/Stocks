package ru.kcoder.stocks.config

import javax.inject.Inject

class TokenSource @Inject constructor() {

    fun getToken(): String {
        return DEFAULT_TOKEN
    }

    companion object {
        // todo not the best way to save api key, should use servers for sensitive data
        private const val DEFAULT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9." +
                "eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBm" +
                "ODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4i" +
                "LCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6" +
                "ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5" +
                "MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg"
    }
}