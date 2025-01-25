package com.example.lab_9

import retrofit2.Call
import android.os.Bundle
import retrofit2.Callback
import retrofit2.Response
import androidx.core.view.ViewCompat
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rView: RecyclerView = findViewById(R.id.rView)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val adapter = Adapter(DiffCallback())
        setSupportActionBar(toolbar)
        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(this)

        val mService: RetrofitServices = Common.retrofitService
        val lat = 54.2021736
        val lon = 30.2964015
        val appid = BuildConfig.OPEN_WEATHER_API_KEY

        mService.getWeatherList(lat, lon, appid).enqueue(object : Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                if (response.isSuccessful) {
                    val forecast = response.body()
                    forecast?.apply { adapter.submitList(list) }
                }
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) { }
        })
    }
}