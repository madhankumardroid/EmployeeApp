package com.example.presentation.feature.employeelist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.CircularProfileImage
import com.example.core_ui.theme.Dimensions
import com.example.domain.model.employeelist.EmployeeListItemModel
import com.example.presentation.R

@Composable
fun EmployeeDetailsScreen(
    employee: EmployeeListItemModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Make the content scrollable
            .padding(Dimensions.dimen_16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Employee Photo
        CircularProfileImage(
            imageSource = employee.photo,
            size = Dimensions.dimen_64,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(Dimensions.dimen_16))

        // Employee Name
        Text(
            text = employee.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Username
        Text(
            text = "@${employee.username}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.dimen_24))

        // Contact Information Section
        SectionTitle(title = stringResource(R.string.contact_information))
        DetailItem(icon = Icons.Default.Email, label = stringResource(R.string.email), value = employee.email)
        DetailItem(icon = Icons.Default.Phone, label = stringResource(R.string.phone), value = employee.phone)

        Spacer(modifier = Modifier.height(Dimensions.dimen_24))

        // Company Information Section
        SectionTitle(title = stringResource(R.string.company_information))
        DetailItem(icon = Icons.Default.Business, label = stringResource(R.string.company), value = employee.company)
        DetailItem(
            icon = Icons.Default.LocationOn,
            label = "Address",
            value = "${employee.address}, ${employee.state} ${employee.zip}"
        )
        DetailItem(icon = Icons.Default.Public, label = stringResource(R.string.country), value = employee.country)

        Spacer(modifier = Modifier.height(Dimensions.dimen_24))

        // Other Details
        // You can add more sections or items as needed
        SectionTitle(title = "Other")
        DetailItem(icon = Icons.Default.Person, label = stringResource(R.string.employee_id), value = employee.id.toString())
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimensions.dimen_8)
    )
    Divider(modifier = Modifier.padding(bottom = 12.dp))
}

@Composable
fun DetailItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.dimen_8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Dimensions.dimen_24)
        )
        Spacer(modifier = Modifier.width(Dimensions.dimen_16))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
