package com.ikaru.algostudiotest.Services

import com.ikaru.algostudiotest.Models.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/get_memes")
    fun getMeme(): Call<DataResponse>

}