package com.example.domain.usecase

import com.example.core.error.Failure
import com.example.core.functional.Either
import com.example.domain.model.employeelist.EmployeeListItemModel
import com.example.domain.repository.EmployeeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class EmployeeListUseCaseTest {

    private val employeeRepository: EmployeeRepository = mockk()
    private lateinit var employeeListUseCase: EmployeeListUseCase

    @BeforeEach
    fun setUp() {
        employeeListUseCase = EmployeeListUseCase(employeeRepository)
    }


    @Test
    fun `Given employee list, When getEmployeeList is called, Then return Either Right`() = runTest {
        val employeeDetails = mockk<EmployeeListItemModel>()

        coEvery { employeeRepository.getEmployeeList() } returns Either.Right(listOf(employeeDetails))

        assert(employeeListUseCase.invoke() == Either.Right(listOf(employeeDetails)))
    }

    @Test
    fun `GIVEN ioexception occurs WHEN use case is invoked THEN NetworkError returned`() = runTest {
        val exception = IOException("Network Error")

        coEvery { employeeRepository.getEmployeeList() } returns Either.Left(Failure.NetworkError(exception))

        assert(employeeListUseCase.invoke() == Either.Left(Failure.NetworkError(exception)))
    }


    @Test
    fun `GIVEN HttpException occurs WHEN use case is invoked THEN ServerError returned`() = runTest {
        coEvery { employeeRepository.getEmployeeList() } returns Either.Left(Failure.ServerError(200,"server"))

        assert(employeeListUseCase.invoke() == Either.Left(Failure.ServerError(200, "server")))
    }
}