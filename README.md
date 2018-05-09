# EasyOKHttp

```gradle
dependencies {
            annotationProcessor 'com.msx7.andriod:aptokhttp:0.1.4'
            implementation 'com.msx7.andriod:commons:0.1.2'
}
```
-- 示例
```demo
public interface DeskAPI {
    @GET("https://www.sojson.com/open/api/weather/json.shtml")
    Observable<Object> getStatistic(@Query("city") String city);
}
```
```
  DeskAPI_Creator.getStatistic("上海") {
            Log.d("MSG",it.data.toString())
            null
        }
```
详细示例:
1、[完整示例1](https://github.com/MsxOpenSource/EasyOKHttp/tree/master/demo)
2、[完整示例2](https://github.com/MsxOpenSource/EasyOKHttp/tree/master/demoforcustomer)
androidStudio -> run -> make modules 或者  gradle assemble  生成类 DeskAPI_Creator.java
