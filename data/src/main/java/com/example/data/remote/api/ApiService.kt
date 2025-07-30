package com.example.data.remote.api

import com.example.data.dto.EmployeeListItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getEmployeeList(): Response<ArrayList<EmployeeListItemDto>>
}