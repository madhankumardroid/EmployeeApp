package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeListItemDto(
    @SerialName("address")
    val address: String?,
    @SerialName("company")
    val company: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("photo")
    val photo: String?,
    @SerialName("state")
    val state: String?,
    @SerialName("username")
    val username: String?,
    @SerialName("zip")
    val zip: String?
)