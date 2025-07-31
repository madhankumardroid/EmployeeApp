# Employee App

An Android app to fetch employee data.

## Table of Contents

- [Overview](#overview)
- [Tech Stack & Libraries](#tech-stack--libraries)
- [Architecture](#architecture)
  - [Clean Architecture](#clean-architecture)
  - [MVI (Model-View-Intent)](#mvi-model-view-intent)
- [Testing](#testing)
  - [Unit Tests](#unit-tests)
- [Project Structure](#project-structure)
- [Setup & Build](#setup--build)
- [Future Enhancements (Optional)](#future-enhancements-optional)
- [Contributing (Optional)](#contributing-optional)

## Overview

This application demonstrates modern Android development practices, including a robust architecture, a reactive UI pattern, and comprehensive testing.

The core feature is to fetch the employee data from a mock API and listing it.

## Tech Stack & Libraries

This project leverages a modern Android tech stack:

*   **UI:**
    *   **Jetpack Compose:** For building the user interface declaratively with Kotlin.
    *   **Material 3 Components:** For modern Material Design styling.
    *   **Compose Navigation:** For handling navigation between screens.
*   **Core:**
    *   **Kotlin:** Primary programming language.
    *   **Coroutines & Flow:** For asynchronous programming and reactive data streams.
*   **Dependency Injection:**
    *   **Hilt:** For managing dependencies throughout the application.
*   **Architecture Components:**
    *   **ViewModel:** To store and manage UI-related data in a lifecycle-conscious way.
*   **Networking:**
    *   **Retrofit:** For type-safe HTTP calls to a backend API.
    *   **OkHttp:** As the underlying HTTP client for Retrofit.
*   **Testing:**
    *   **JUnit 5:** For unit testing.
    *   **MockK:** For creating mocks and verifying interactions in unit tests.
    *   **Turbine:** For testing Kotlin Flows.
    *   **AndroidX Test Libraries:** For core Android testing utilities.
    *   **Kotest:** For readable, Kotlin DSL based unit tests.
*   **Build System:**
    *   **Gradle:** With Kotlin DSL (`build.gradle.kts`).

## Architecture

The application follows a combination of Clean Architecture principles and the Model-View-Intent (MVI) pattern for the presentation layer.

### Clean Architecture

Clean Architecture is employed to create a separation of concerns, making the application more modular, testable, and maintainable. The project is typically divided into the following layers (or modules):

*   **Domain Layer (`:domain` module):**
    *   Contains business logic, use cases, and domain models.
    *   Pure Kotlin module with no Android framework dependencies.
    *   Defines repository interfaces that the data layer implements.
    *   **Example:** `EmployeeListUseCase`, `EmployeeListItemModel` (domain model), `EmployeeRepository` (interface).

*   **Data Layer (`:data` module):**
    *   Implements the repository interfaces defined in the domain layer.
    *   Responsible for fetching data from various sources (network, local database).
    *   Contains repository implementations, and data mapping logic (DTOs to domain models).
    *   Depends on libraries like Retrofit.
    *   **Example:** `EmployeeRepositoryImpl`, `ApiService`, `EmployeeListMapper`.

*   **Presentation Layer (UI Layer):**
    *   Handles UI logic and user interaction.
    *   Uses ViewModels that execute use cases from the domain layer.
    *   Observes data changes and updates the UI (Jetpack Compose views).
    *   Implements the MVI pattern.
    *   **Example:** `EmployeeListViewModel`, `EmployeeListScreen` (Compose), `EmployeeDetailScreen` (Compose).

**Benefits:**
*   **Testability:** Each layer can be tested independently.
*   **Maintainability:** Changes in one layer have minimal impact on others.
*   **Independence:** The core business logic (domain) is independent of UI and data source specifics.

### MVI (Model-View-Intent)

The MVI pattern is used within the presentation layer to manage UI state and user interactions in a unidirectional data flow.

*   **Model:** Represents the state of the UI. It's typically an immutable data class.
    *   **Example:** `EmployeeListContract.State` (containing loading status, employee list, error messages).

*   **View:** The UI (Jetpack Compose screens) that observes the Model (State) and renders it. It also captures user inputs (Intents).
    *   **Example:** `EmployeeListScreen` observing the `State` from the `EmployeeListViewModel`.

*   **Intent:** Represents user actions or events that can modify the state. These are dispatched from the View to the ViewModel.
    *   **Example:** `EmployeeListContract.Event.LoadEmployees`, `EmployeeListContract.Event.EmployeeClicked`.

*   **ViewModel:**
    *   Listens to Intents from the View.
    *   Processes these Intents by interacting with Use Cases from the domain layer.
    *   Updates the Model (State) based on the results.
    *   Emit one-time side effects for actions like navigation.
    *   **Example:** `EmployeeListViewModel` handling `LoadEmployees` event, calling `EmployeeListUseCase`, and updating the `State`.

**Unidirectional Data Flow:**
`View (Intent) -> ViewModel -> Use Case (Domain) -> Repository (Data) -> Use Case -> ViewModel (updates State) -> View (renders new State)`

**Benefits:**
*   **Predictable State:** State changes are centralized and follow a clear path.
*   **Testability:** ViewModels can be easily unit tested by providing Intents and asserting State changes or Effects.
*   **Debugging:** Easier to trace how the UI state changes.

## Testing

Testing is a crucial part of this project to ensure code quality and reliability.

### Unit Tests

Unit tests are written to verify the functionality of individual components in isolation.

*   **Domain Layer:**
    *   MockK is used to mock repository dependencies.
    *   `kotlinx-coroutines-test` is used for testing coroutines and Flows (e.g., using `runTest`).
    *   **Example:** `EmployeeListUseCaseTest` verifies that the use case returns the expected data or error states based on repository responses.

*   **Presentation Layer (ViewModels):**
    *   MockK is used to mock use case dependencies.
    *   Kotest's FeatureSpec is used to write feature specific test cases.
    *   Turbine is used for testing `StateFlow` or `SharedFlow` emissions (UI State and Effects).
    *   **Example:** `EmployeeListViewModelTest` verifies that when a `LoadEmployees` event is received, the ViewModel calls the appropriate use case and updates the state to show loading, then success (with data) or error.

*   **Data Layer:**
    *   MockK is used to mock ApiService and Mapper dependencies.
    *   Mappers, network handler, and error handler are tested.
    *   Kotest's FunSpec is used to write test cases.

**Location:** Unit tests are typically found in the `src/test/java` (or `src/test/kotlin`) directory of each module.

## Project Structure

The project is organized into modules based on Clean Architecture principles:

*   `:app` - Contains Android application specific code like Application, MainActivity.
*   `:core-ui` - UI (Compose screens), UI Components, ViewModels.
*   `:domain` - Contains business logic (UseCases) and domain models
*   `:data` - Contains repository implementations, network handler, error handler and DTOs.
*   `:core` - Contains common utilities and functional constructs like `Either` used across modules.

## Setup & Build

1.  Clone the repository: `git clone https://github.com/madhankumardroid/EmployeeApp.git`
2.  Open the project in Android Studio (Built and tested with Android Studio Narwhal | 2025.1.1).
3.  Let Gradle sync and download dependencies.
4.  Build the project: `Build > Make Project` or use the Gradle command: `./gradlew assembleDebug`.
5.  Run the app on an emulator or a physical device.
6.  To run unit tests: `./gradlew testDebugUnitTest` (or run from Android Studio's test runner).
7.  To run unit tests in `data` and `presentation` module. Need to install `kotest` plugin.
