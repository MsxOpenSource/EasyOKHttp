# EasyOKHttp

```gradle
apply plugin: 'kotlin-android'
apply plugin: "org.jetbrains.kotlin.kapt"
dependencies {
    kapt 'com.msx7.andriod:aptokhttp:0.1.1'
    implementation 'com.msx7.andriod:commons:0.1.1'
}
```

```demo
package com.msx7.android.okhttpapt;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DeskAPI {
    @GET("https://www.sojson.com/open/api/weather/json.shtml")
    Observable<Object> getStatistic(@Query("city") String city);
}

  DeskAPI_Creator.getStatistic("上海") {
            Log.d("MSG",it.data.toString())
            null
        }
```
androidStudio -> run -> make modules or  gradle assemble  generate DeskAPI_Creator.java
