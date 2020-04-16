package com.example.covid19_india

import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    public val ohttpclient=OkHttpClient()
    public val request=Request.Builder().url("https://api.covid19india.org/data.json").build()
    public val api= ohttpclient.newCall(request)
}