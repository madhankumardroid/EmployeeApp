package com.example.data.mapper

import com.example.core.mapper.ResultMapper
import com.example.data.dto.EmployeeListItemDto
import com.example.domain.model.employeelist.EmployeeListItemModel
import javax.inject.Inject

class EmployeeListMapper @Inject constructor() : ResultMapper<List<EmployeeListItemDto>, List<EmployeeListItemModel>> {
    override fun map(input: List<EmployeeListItemDto>): List<EmployeeListItemModel> {
        return input.map{it.toModel()}
    }
}

private fun EmployeeListItemDto.toModel(): EmployeeListItemModel {
    return EmployeeListItemModel(
        id = id,
        address = address ?: "",
        company = company ?: "",
        country = country ?: "",
        email = email ?: "",
        name = name ?: "",
        phone = phone ?: "",
        photo = photo ?: "",
        state = state ?: "",
        username = username ?: "",
        zip = zip ?: ""

    )
}
