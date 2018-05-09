package com.msx7.android.okhttpapt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DeskAPI_Creator.getStatistic("上海") {
            Log.d("MSG",it.data.toString())
            null
        }
    }
}
