package com.example.presentation.feature.employeelist.mvi

import com.example.core.error.Failure
import com.example.core_ui.mvi.MVIContract
import com.example.domain.model.employeelist.EmployeeListItemModel

interface EmployeeListContract : MVIContract<EmployeeListContract.EmployeeListState, EmployeeListContract.EmployeeListEffect, EmployeeListContract.EmployeeListEvent> {
    sealed class EmployeeListEvent {
        data object LoadEmployeeList : EmployeeListEvent()

        data class EmployeeClicked(val model: EmployeeListItemModel) : EmployeeListEvent()
    }

    sealed class EmployeeListState {
        data object Loading : EmployeeListState()

        data class Success(
            val employeeList: List<EmployeeListItemModel>
        ) : EmployeeListState()

        data class Error(
            val error: Failure
        ) : EmployeeListState()
    }

    sealed class EmployeeListEffect {
        data class NavigateToEmployeeDetails(val model: EmployeeListItemModel) : EmployeeListEffect()
    }
}