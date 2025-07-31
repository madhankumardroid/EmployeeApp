package com.example.data.remote.repository

import com.example.core.error.Failure
import com.example.core.functional.Either
import com.example.data.dto.EmployeeListItemDto
import com.example.data.mapper.EmployeeListMapper
import com.example.data.remote.api.ApiService
import com.example.data.repository.EmployeeRepositoryImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import retrofit2.Response
import java.io.IOException

class EmployeeRepositoryImplTest : FunSpec() {
    private val apiService: ApiService = mockk()
    private val employeeListMapper: EmployeeListMapper = mockk()
    private val employeeRepository =
        EmployeeRepositoryImpl(apiService, employeeListMapper)

    init {
        test("getEmployeeList should return NetworkError for IOException") {
            val exp = IOException("Network Error")
            coEvery { apiService.getEmployeeList() } throws exp
            val result = employeeRepository.getEmployeeList()
            result shouldBe Either.Left(Failure.NetworkError(exp))
        }

        test("getEmployeeList should return mapped data on success") {
            val mockResponse = Response.success(arrayListOf<EmployeeListItemDto>())
            coEvery { apiService.getEmployeeList() } returns mockResponse
            coEvery { employeeListMapper.map(any()) } returns listOf()

            val result = employeeRepository.getEmployeeList()
            result shouldBe Either.Right(listOf())
        }
    }

}