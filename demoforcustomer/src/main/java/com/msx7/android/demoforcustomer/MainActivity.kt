package com.msx7.android.demoforcustomer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text) as TextView
        textView.setOnClickListener {
            getWeather()
        }
        getWeather()
    }

    fun getWeather() {
        DeskAPI_Creator.getStatistic("上海") {
            when (it.isSuccess) {
                true -> {

                    textView.text = it.data.data.toString()
                }
                else -> {
                    textView.text = it.error
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                }
            }
            null
        }
    }

}
