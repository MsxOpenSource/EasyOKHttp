package com.msx7.android.okhttpapt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.msx7.android.commons.MConfig
import com.msx7.android.commons.MRetorfit
import okhttp3.Interceptor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        config()
        DeskAPI_Creator.getStatistic("上海") {
            Log.d("MSG",it.data.toString())
            null
        }
    }

    /**
     * 可以不调用此方法，需要retrofit2定义的相关GET、POST等接口中，设置完整的URL
     */
    private fun config() {
        val config = MConfig("https://www.sojson.com/")
        var interceptors: Array<Interceptor> = arrayOf()
        if (BuildConfig.DEBUG) interceptors = interceptors.plus(StethoInterceptor())
        config.addInterceptor(*interceptors)
//  通过此方法添加 addNetInterceptor      config.addNetInterceptor()
        MRetorfit.getInstance().config(config)
    }
}
