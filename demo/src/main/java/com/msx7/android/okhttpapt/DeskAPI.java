package com.msx7.android.okhttpapt;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DeskAPI {
    @GET("http://test9.525j.com.cn/app/empapi/v1.0/emp.reservation.statistics")
    Observable<Object> getStatistic(@Query("cityid") String cityid,
                                    @Query("employeeid") String employeeid);
}
