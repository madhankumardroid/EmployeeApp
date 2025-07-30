package com.example.employeeapp.navigation

object NavDestinations {
    const val EMPLOYEE_LIST = "employeeList"
    const val EMPLOYEE_DETAIL_ROUTE = "employeeDetail"
    const val ARG_EMPLOYEE_ID = "employeeId" // Argument key
    const val EMPLOYEE_DETAIL = "$EMPLOYEE_DETAIL_ROUTE/{$ARG_EMPLOYEE_ID}"

    fun employeeDetailPath(employeeId: Int): String {
        return "$EMPLOYEE_DETAIL_ROUTE/$employeeId"
    }
}