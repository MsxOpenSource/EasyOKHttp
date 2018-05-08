package com.msx7.android.commons

import com.msx7.android.annotions.HRetrofit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HRetrofit
class MRetorfit {


    private lateinit var retrofit: Retrofit
    private var client: OkHttpClient? = null

    fun config(config: MConfig) {

        val builder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        config.interceptor.forEach {
            builder.addInterceptor(it)
        }
        config.netInterceptor.forEach {
            builder.addNetworkInterceptor(it)
        }
        client = builder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

    }

    companion object {
        val instance: MRetorfit by lazy { MRetorfit() }

        fun retrofit(): Retrofit {
            instance.client ?: instance.config(MConfig())
            return instance.retrofit
        }
    }


}
