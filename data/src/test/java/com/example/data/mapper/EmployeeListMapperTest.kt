package com.example.data.mapper

import com.example.data.dto.EmployeeListItemDto
import com.example.domain.model.employeelist.EmployeeListItemModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeListMapperTest {
    private lateinit var mapper: EmployeeListMapper

    @BeforeEach
    fun setUp() {
        mapper = EmployeeListMapper()
    }

    @ParameterizedTest
    @MethodSource("provideDataForMapper")
    fun `Given empty input WHEN mapped THEN output is empty`(
        input: List<EmployeeListItemDto>,
        output: List<EmployeeListItemModel>
    ) {
        val result = mapper.map(input)

        result shouldBe output
    }

    private companion object {

        const val TEST_ID = 1
        @JvmStatic
        fun provideDataForMapper(): Stream<Arguments> = Stream.of(
            testDataWithEmptyInputOutput(),
            testDataWithNullId(),
            testDataWithValidInput(),
            testDataToCheckTheDefaultValue(),
        )

        private fun getEmployeeListItemDto(
            address: String? = null,
            id: Int = -1,
            company: String? = null,
            country: String? = null,
            email: String? = null,
            name: String? = null,
            phone: String? = null,
            photo: String? = null,
            state: String? = null,
            username: String? = null,
            zip: String? = null,
        ) = EmployeeListItemDto(
            address = address,
            company = company,
            country = country,
            email = email,
            id = id,
            name = name,
            phone = phone,
            photo = photo,
            state = state,
            username = username,
            zip = zip,
        )

        private fun testDataWithValidInput() = Arguments.of(
            listOf(
                getEmployeeListItemDto(
                    address = "7097 Cedar Close",
                    id = TEST_ID,
                    company = "Lind - Boyle",
                    country = "Thailand",
                    email = "Jackie.Stoltenberg@gmail.com",
                    name = "Jamarcus Ratke",
                    phone = "663.225.4264 x18861",
                    photo = "https://json-server.dev/ai-profiles/86.png",
                    state = "Wyoming",
                    zip = "60620",
                    username = "Desmond.Hodkiewicz-Hickle92"
                )
            ), listOf(
                EmployeeListItemModel(
                 id = TEST_ID,
                    country = "Thailand",
                    company = "Lind - Boyle",
                    email = "Jackie.Stoltenberg@gmail.com",
                    name = "Jamarcus Ratke",
                    phone = "663.225.4264 x18861",
                    photo = "https://json-server.dev/ai-profiles/86.png",
                    state = "Wyoming",
                    username = "Desmond.Hodkiewicz-Hickle92",
                    zip = "60620",
                    address = "7097 Cedar Close"
                )
            )
        )
        private fun testDataToCheckTheDefaultValue() = Arguments.of(
            listOf(
                getEmployeeListItemDto(
                    address = null,
                    id = TEST_ID,
                    company = null,
                    country = null,
                    email = "Jackie.Stoltenberg@gmail.com",
                    name = "Jamarcus Ratke",
                    phone = "663.225.4264 x18861",
                    photo = "https://json-server.dev/ai-profiles/86.png",
                    state = "Wyoming",
                    zip = "60620",
                    username = "Desmond.Hodkiewicz-Hickle92"
                )
            ), listOf(
                EmployeeListItemModel(
                 id = TEST_ID,
                    country = "",
                    company = "",
                    email = "Jackie.Stoltenberg@gmail.com",
                    name = "Jamarcus Ratke",
                    phone = "663.225.4264 x18861",
                    photo = "https://json-server.dev/ai-profiles/86.png",
                    state = "Wyoming",
                    username = "Desmond.Hodkiewicz-Hickle92",
                    zip = "60620",
                    address = ""
                )
            )
        )

        private fun testDataWithEmptyInputOutput() = Arguments.of(emptyList<EmployeeListItemDto>(), emptyList<EmployeeListItemModel>())

        private fun testDataWithNullId() =  Arguments.of(listOf(getEmployeeListItemDto()), emptyList<EmployeeListItemModel>())

    }
}