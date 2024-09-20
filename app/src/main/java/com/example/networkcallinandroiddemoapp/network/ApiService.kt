package com.example.networkcallinandroiddemoapp.network

import com.example.networkcallinandroiddemoapp.models.DummyDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    fun getDummyData(): Call<List<DummyDataResponse>>

}