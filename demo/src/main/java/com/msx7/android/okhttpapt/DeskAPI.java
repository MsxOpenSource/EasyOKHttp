package com.msx7.android.okhttpapt;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DeskAPI {
    @GET("https://www.sojson.com/open/api/weather/json.shtml")
    Observable<Object> getStatistic(@Query("city") String city);
}
