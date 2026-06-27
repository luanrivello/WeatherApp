package com.example.findinglogs.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.findinglogs.R
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.view.recyclerview.adapter.WeatherListAdapter
import com.example.findinglogs.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var adapter: WeatherListAdapter? = null
    private val weathers: List<Weather> = ArrayList()
    private var fetchButton: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainViewModel = ViewModelProvider(this).get(
            MainViewModel::class.java
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_weather)
        fetchButton = findViewById(R.id.fetchButton)
        adapter = WeatherListAdapter(this, weathers)
        recyclerView.adapter = adapter
        mainViewModel.weatherList.observe(
            this,
            Observer { weathers: List<Weather?>? -> adapter!!.updateWeathers(weathers) })

        fetchButton.setOnClickListener(View.OnClickListener { v: View? ->
            Toast.makeText(
                this@MainActivity, "Not Implemenented yet",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}