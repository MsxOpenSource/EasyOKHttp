package com.msx7.android.demoforcustomer;

import com.msx7.android.annotions.Convert;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Convert
public class OkHttpSubcribe {
    public static final <T> Observable<T> subscribeOn(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
