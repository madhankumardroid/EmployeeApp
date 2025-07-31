package com.example.presentation.feature.employeelist.viewmodel

import com.example.core.error.Failure
import com.example.core.functional.Either
import com.example.domain.model.employeelist.EmployeeListItemModel
import com.example.domain.usecase.EmployeeListUseCase
import com.example.presentation.feature.employeelist.mvi.EmployeeListContract
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class EmployeeViewModelTest : FeatureSpec({
    lateinit var employeeListUseCase: EmployeeListUseCase
    lateinit var viewModel : EmployeeViewModel
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        employeeListUseCase = mockk<EmployeeListUseCase>()
        viewModel = EmployeeViewModel(employeeListUseCase)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    feature("LoadEmployees event") {
        scenario("should update state to Success when use case returns data") {
            val employeeList = listOf(mockk<EmployeeListItemModel>())
            coEvery { employeeListUseCase.invoke() } returns Either.Right(employeeList)

            runTest {
                val states = mutableListOf<EmployeeListContract.EmployeeListState>()

                val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.toList(states)
                }
                viewModel.event(EmployeeListContract.EmployeeListEvent.LoadEmployeeList)
                testScheduler.advanceUntilIdle()
                collectJob.cancel()
                states shouldBe listOf(
                    EmployeeListContract.EmployeeListState.Loading,
                    EmployeeListContract.EmployeeListState.Success(employeeList)
                )
            }
        }

        scenario("should update state to Error when use case returns failure") {
            val failure = Failure.ServerError(500, "Server Error")
            coEvery { employeeListUseCase.invoke() } returns Either.Left(failure)

            runTest {
                val states = mutableListOf<EmployeeListContract.EmployeeListState>()

                val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.toList(states)
                }

                viewModel.event(EmployeeListContract.EmployeeListEvent.LoadEmployeeList)
                testScheduler.advanceUntilIdle()
                collectJob.cancel()
                states shouldBe listOf(
                    EmployeeListContract.EmployeeListState.Loading,
                    EmployeeListContract.EmployeeListState.Error(failure)
                )
            }
        }
    }

    feature("EmployeeClicked event") {
        scenario("should emit NavigateToEmailDetails effect") {
            val employee = mockk<EmployeeListItemModel>()
            val event = EmployeeListContract.EmployeeListEvent.EmployeeClicked(employee)

            runTest {
                val effects = mutableListOf<EmployeeListContract.EmployeeListEffect>()

                val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.effect.collect { effect ->
                        effects.add(effect)
                    }
                }

                viewModel.event(event)
                testScheduler.advanceUntilIdle()
                collectJob.cancel()

                effects shouldBe listOf(
                    EmployeeListContract.EmployeeListEffect.NavigateToEmployeeDetails(employee)
                )
            }
        }
    }
})