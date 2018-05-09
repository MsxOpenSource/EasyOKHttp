package com.msx7.android.commons;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * 所 属 包：com.lovehome.android.aijiazhusou.common.flux
 * 文 件 名：MConfig
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2018/5/8
 */
public class MConfig {
    String            baseUrl         = "https://www.github.com";
    List<Interceptor> interceptors    = new LinkedList();
    List<Interceptor> netInterceptors = new LinkedList();


    public MConfig() {
    }

    public MConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public MConfig(String baseUrl, Interceptor... interceptor) {
        this(baseUrl);
        this.interceptors.addAll(Arrays.asList(interceptor));
    }

    public MConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public MConfig addInterceptor(Interceptor... interceptor) {
        this.interceptors.addAll(Arrays.asList(interceptor));
        return this;
    }

    public MConfig addNetInterceptor(Interceptor... interceptor) {
        this.netInterceptors.addAll(Arrays.asList(interceptor));
        return this;
    }
}

