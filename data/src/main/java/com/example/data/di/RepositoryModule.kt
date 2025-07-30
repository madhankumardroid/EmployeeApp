package com.example.data.di

import com.example.data.repository.EmployeeRepositoryImpl
import com.example.domain.repository.EmployeeRepository
import dagger.hilt.InstallIn
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEmployeeRepository(employeeRepositoryImpl: EmployeeRepositoryImpl): EmployeeRepository
}
