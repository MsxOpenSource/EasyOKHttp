package com.msx7.android.demoforcustomer;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DeskAPI {
    @GET("/open/api/weather/json.shtml")
    Observable<BaseBean> getStatistic(@Query("city") String city);
}
