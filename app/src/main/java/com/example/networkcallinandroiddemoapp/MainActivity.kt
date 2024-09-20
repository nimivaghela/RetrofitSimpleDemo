package com.example.networkcallinandroiddemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.networkcallinandroiddemoapp.models.DummyDataResponse
import com.example.networkcallinandroiddemoapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var apiService: ApiService
    var list: List<DummyDataResponse>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val retrofit = Retrofit.Builder().baseUrl("https://api.restful-api.dev/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        apiService = retrofit.create(ApiService::class.java)

        getExampleData()
    }

    private fun getExampleData() {
        val call = apiService.getDummyData()
        call.enqueue(object : Callback<List<DummyDataResponse>> {
            override fun onResponse(
                call: Call<List<DummyDataResponse>>,
                response: Response<List<DummyDataResponse>>
            ) {
                if (response.isSuccessful) {
                    list = response.body()
                    for (i in 0..list!!.size - 1) {

                        Log.d("TAG", "onResponse: ${response.body()?.get(i)!!.name}")
                    }
                    setData()

                } else {
                    Log.d("TAG", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DummyDataResponse>>, t: Throwable) {

            }
        })
    }

    private fun setData() {
        val nameList: List<String> = list!!.map { it.name }
        findViewById<ListView>(R.id.listView).adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList)

    }
}