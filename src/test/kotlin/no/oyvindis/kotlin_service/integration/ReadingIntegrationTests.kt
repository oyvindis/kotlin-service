package no.oyvindis.kotlin_service.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.oyvindis.kotlin_service.utils.*
import no.oyvindis.kotlin_service.utils.jwk.JwtToken
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder

private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
private val dateTimeSerializer = LocalDateTimeSerializer(formatter)

private val mapper: ObjectMapper = jacksonObjectMapper()
    .registerModule(
        JavaTimeModule()
            .addSerializer(LocalDateTime::class.java, dateTimeSerializer)
    )
    .registerModule(Jdk8Module ())

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    properties = ["spring.profiles.active=integration-test"],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = [ApiTestContext.Initializer::class])
@Tag("integration")
class ReadingIntegrationTests: ApiTestContext() {

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Test
    fun `Unauthorized when access token is not included`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/climate-api/reading/${LOCATION_ID_1000}"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `Ok - get readings`() {
        val response = mockMvc.perform(MockMvcRequestBuilders
            .get("/climate-api/reading/${LOCATION_ID_1000}")
            .header("Authorization", "Bearer " + JwtToken().toString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        val responseList = mapper.readValue(response.andReturn().response.contentAsString, List::class.java)
        assertEquals(responseList.size, 1)
    }

}