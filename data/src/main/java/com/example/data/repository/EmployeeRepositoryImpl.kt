package com.example.data.repository

import com.example.core.error.Failure
import com.example.core.functional.Either
import com.example.data.mapper.EmployeeListMapper
import com.example.data.remote.api.ApiService
import com.example.data.remote.handler.safeApiCall
import com.example.domain.model.employeelist.EmployeeListItemModel
import com.example.domain.repository.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val employeeListMapper: EmployeeListMapper,
) : EmployeeRepository {
    override suspend fun getEmployeeList(): Either<Failure, List<EmployeeListItemModel>> =
        safeApiCall(
            apiCall = { apiService.getEmployeeList() },
            mapper = { employeeListMapper.map(it) }
        )
}