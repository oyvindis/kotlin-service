package no.oyvindis.kotlin_service.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.oyvindis.kotlin_service.utils.ApiTestContext
import no.oyvindis.kotlin_service.utils.apiGet
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
val dateTimeSerializer = LocalDateTimeSerializer(formatter)

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
class LocationIntegration : ApiTestContext() {

    @Test
    fun `Ok - read location`() {
        val response = apiGet(port, "/climate-api/location", "application/json")
        assertEquals(HttpStatus.OK.value(), response["status"])

        val responseList = mapper.readValue(response["body"] as String, List::class.java)
        assertEquals(responseList.size, 1)
    }
}
