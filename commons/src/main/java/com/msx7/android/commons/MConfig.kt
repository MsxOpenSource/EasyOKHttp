package com.msx7.android.commons

import okhttp3.Interceptor

class MConfig {
    constructor()
    constructor(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    constructor(baseUrl: String, vararg interceptor: Interceptor) {
        this.baseUrl = baseUrl;
        this.interceptor = this.interceptor.plus(interceptor)
    }

    var baseUrl: String = "https://www.github.com"
    var interceptor: Array<Interceptor> = arrayOf()
    var netInterceptor: Array<Interceptor> = arrayOf()

    fun baseUrl(url: String): MConfig {
        this.baseUrl = url
        return this
    }

    fun addInterceptor(interceptor: Interceptor): MConfig {
        this.interceptor = this.interceptor.plus(interceptor)
        return this
    }

    fun addInterceptor(vararg interceptor: Interceptor): MConfig {
        this.interceptor = this.interceptor.plus(interceptor)
        return this
    }

    fun addNetInterceptor(vararg interceptor: Interceptor): MConfig {
        this.netInterceptor = netInterceptor.plus(interceptor)
        return this
    }

    fun addNetInterceptor(interceptor: Interceptor): MConfig {
        this.netInterceptor = netInterceptor.plus(interceptor)
        return this
    }

}