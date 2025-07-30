package com.example.employeeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.employeeapp.navigation.NavDestinations
import com.example.employeeapp.ui.theme.EmployeeAppTheme
import com.example.presentation.feature.employeelist.mvi.EmployeeListContract
import com.example.presentation.feature.employeelist.screens.EmployeeDetailsScreen
import com.example.presentation.feature.employeelist.screens.EmployeeListScreen
import com.example.presentation.feature.employeelist.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmployeeAppTheme {
                val navController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    rememberTopAppBarState()
                )
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val isListScreen = currentDestination?.hierarchy?.any {
                    it.route == NavDestinations.EMPLOYEE_LIST
                } == true && navController.previousBackStackEntry == null


                // Determine the title based on the current screen
                val appBarTitle = when {
                    currentDestination?.route?.startsWith(NavDestinations.EMPLOYEE_DETAIL.substringBefore("/{")) == true -> stringResource(
                        R.string.employee_details
                    ) // Or dynamically set employee name
                    else -> stringResource(R.string.my_employees)
                }
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = { Text(appBarTitle) },
                        navigationIcon = {
                            if (!isListScreen) { // Show back arrow if not on the list screen
                                IconButton(onClick = { navController.navigateUp() }) { // navigateUp is standard for back
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            } else {
                                // Optional: Show Menu icon on the list screen
                                IconButton(onClick = { /* TODO: Handle menu icon press */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Navigation Menu"
                                    )
                                }
                            }
                        },
                        scrollBehavior = scrollBehavior, // Attach the scroll behavior
                        colors = TopAppBarDefaults.topAppBarColors( // Optional: Customize colors
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }) { innerPadding ->
                    val viewModel: EmployeeViewModel = hiltViewModel()
                    NavHost(
                        navController = navController,
                        startDestination = NavDestinations.EMPLOYEE_LIST,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Employee List Screen
                        composable(NavDestinations.EMPLOYEE_LIST) {
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val dispatch: (EmployeeListContract.EmployeeListEvent) -> Unit =
                                { event ->
                                    viewModel.event(event)
                                }
                            LaunchedEffect(key1 = Unit) {
                                viewModel.effect.collect { it ->
                                    when (it) {
                                        is EmployeeListContract.EmployeeListEffect.NavigateToEmployeeDetails -> {
                                            val employeeDetailRoute = NavDestinations.employeeDetailPath(it.model.id)
                                            navController.navigate(employeeDetailRoute)
                                        }
                                    }
                                }
                            }
                            EmployeeListScreen(
                                state = state, // In real app, from ViewModel
                                dispatch = dispatch
                            )
                        }

                        // Employee Detail Screen
                        composable(
                            route = NavDestinations.EMPLOYEE_DETAIL,
                            arguments = listOf(navArgument(NavDestinations.ARG_EMPLOYEE_ID) {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val employeeId =
                                backStackEntry.arguments?.getInt(NavDestinations.ARG_EMPLOYEE_ID)
                            val employeeDetails =
                                employeeId?.let { viewModel.getEmployeeDetailsById(it) }
                            employeeDetails?.let { empDetails ->
                                EmployeeDetailsScreen(
                                    // Using the EmployeeDetailsScreen from previous example
                                    employee = empDetails,
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}