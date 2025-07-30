package com.example.presentation.feature.employeelist.screens

import android.util.Log
import androidx.annotation.Dimension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.core.error.getErrorMessage
import com.example.core_ui.component.EmployeeItem
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LinearFullScreenProgress
import com.example.core_ui.theme.Dimensions
import com.example.presentation.feature.employeelist.mvi.EmployeeListContract
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@Composable
fun EmployeeListScreen(
    state: EmployeeListContract.EmployeeListState,
    dispatch: (EmployeeListContract.EmployeeListEvent) -> Unit
) {
    when (state) {
        is EmployeeListContract.EmployeeListState.Error -> ErrorScreen(
            errorMessage = state.error.getErrorMessage()
        )

        is EmployeeListContract.EmployeeListState.Loading -> LinearFullScreenProgress(
            modifier = Modifier.semantics {
                contentDescription = "Loading"
            }
        )

        is EmployeeListContract.EmployeeListState.Success -> EmployeeList(state, dispatch)
    }
}

@Composable
fun EmployeeList(
    states: EmployeeListContract.EmployeeListState.Success,
    dispatch: (EmployeeListContract.EmployeeListEvent) -> Unit
) {
    Log.d("EmployeeList", "Number of employees: ${states.employeeList.size}") // Add this
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.dimen_16),
        verticalArrangement = Arrangement.spacedBy(Dimensions.dimen_8)
    ) {
        items(items = states.employeeList, key = { it.id }) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(), // Example: make the card take full width
                shape = RoundedCornerShape(Dimensions.dimen_16),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = Dimensions.dimen_4
                )
            ) {
                EmployeeItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimensions.dimen_8)
                        .clickable {
                            dispatch.invoke(EmployeeListContract.EmployeeListEvent.EmployeeClicked(item))
                        }, profileImageUrl = item.photo,
                    employeeName = item.name,
                    companyName = item.company
                )
            }
        }
    }
}