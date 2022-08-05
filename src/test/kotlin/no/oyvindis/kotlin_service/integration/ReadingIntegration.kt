package no.oyvindis.kotlin_service.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.oyvindis.kotlin_service.utils.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime
import kotlin.test.assertEquals


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
class ReadingIntegration: ApiTestContext() {

    @Test
    fun `Ok - get readings`() {
        val response = apiGet(port, "/climate-api/reading/${LOCATION_ID_1000}", "application/json")
        assertEquals(HttpStatus.OK.value(), response["status"])

        val responseList = mapper.readValue(response["body"] as String, List::class.java)
        assertEquals(responseList.size, 1)
    }

}