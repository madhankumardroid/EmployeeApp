package com.example.domain.usecase

import com.example.domain.repository.EmployeeRepository
import javax.inject.Inject

class EmployeeListUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke() = repository.getEmployeeList()
}