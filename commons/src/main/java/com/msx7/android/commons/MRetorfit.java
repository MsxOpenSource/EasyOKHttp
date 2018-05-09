package com.msx7.android.commons;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MRetorfit {

    private Retrofit     retrofit;
    private OkHttpClient client;

    public void config(MConfig config) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);
        for (Interceptor interceptor : config.interceptors) {
            builder.addInterceptor(interceptor);
        }
        for (Interceptor interceptor : config.netInterceptors) {
            builder.addNetworkInterceptor(interceptor);
        }

        client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }


    private static MRetorfit instance;

    private MRetorfit() {
    }

    public static final MRetorfit getInstance() {
        synchronized (MRetorfit.class) {
            if (instance == null) instance = new MRetorfit();
            return instance;
        }
    }

    public static final Retrofit retrofit() {
        if (getInstance().client == null) getInstance().config(new MConfig());
        return getInstance().retrofit;
    }
}
