package com.example.presentation.feature.employeelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.functional.fold
import com.example.domain.model.employeelist.EmployeeListItemModel
import com.example.domain.usecase.EmployeeListUseCase
import com.example.presentation.feature.employeelist.mvi.EmployeeListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val getEmployeesUsecase: EmployeeListUseCase
) : ViewModel(), EmployeeListContract {
    private val _mutableUIState: MutableStateFlow<EmployeeListContract.EmployeeListState> =
        MutableStateFlow(EmployeeListContract.EmployeeListState.Loading)
    private val _mutableSharedFlow: MutableSharedFlow<EmployeeListContract.EmployeeListEffect> =
        MutableSharedFlow()
    private var employeeList: List<EmployeeListItemModel> = emptyList()
    override val state: StateFlow<EmployeeListContract.EmployeeListState>
        get() = _mutableUIState
    override val effect: SharedFlow<EmployeeListContract.EmployeeListEffect>
        get() = _mutableSharedFlow.asSharedFlow()

    override fun event(event: EmployeeListContract.EmployeeListEvent) {
        when (event) {
            is EmployeeListContract.EmployeeListEvent.LoadEmployeeList -> {
                loadEmployees()
            }

            is EmployeeListContract.EmployeeListEvent.EmployeeClicked -> {
                viewModelScope.launch {
                    _mutableSharedFlow.emit(
                        EmployeeListContract.EmployeeListEffect.NavigateToEmployeeDetails(
                            event.model
                        )
                    )
                }
            }
        }
    }

    private fun loadEmployees() {
        _mutableUIState.update { EmployeeListContract.EmployeeListState.Loading }
        viewModelScope.launch {
            getEmployeesUsecase().fold(
                { updateState(EmployeeListContract.EmployeeListState.Error(it)) },
                {
                    updateState(EmployeeListContract.EmployeeListState.Success(employeeList = it)
                    )
                    employeeList = it}
            )
        }
    }

    private fun updateState(state: EmployeeListContract.EmployeeListState) {
        _mutableUIState.update { state }
    }

    fun getEmployeeDetailsById(id: Int) =
       employeeList.find { it.id == id }
}