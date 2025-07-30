package com.example.domain.repository

import com.example.core.error.Failure
import com.example.core.functional.Either
import com.example.domain.model.employeelist.EmployeeListItemModel

interface EmployeeRepository {
    suspend fun getEmployeeList() : Either<Failure, List<EmployeeListItemModel>>
}