package com.msx7.android.demoforcustomer;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.msx7.android.annotions.HRetrofit;
import com.msx7.android.commons.MConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@HRetrofit
public class MRetorfit {

    private Retrofit     retrofit;
    private OkHttpClient client;

    public void config(MConfig config) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new StethoInterceptor());
        }
        client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.sojson.com")
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
