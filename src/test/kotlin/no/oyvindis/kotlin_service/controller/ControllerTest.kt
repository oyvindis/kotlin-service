package no.oyvindis.kotlin_service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import no.oyvindis.kotlin_service.utils.jwk.JwtToken
import no.oyvindis.kotlin_service.utils.jwk.startMockOAuthServer
import no.oyvindis.kotlin_service.utils.jwk.stopMockOAuthServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

/**
 * Base class for Controller tests
 */
@WebMvcTest
@ActiveProfiles("integration-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ControllerTest {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected val authoriazation = "Bearer " + JwtToken().encoded

    protected fun Any.toJson(): String = objectMapper.writeValueAsString(this)

    @BeforeAll
    fun beforeAll() {
        startMockOAuthServer()
    }

    @AfterAll
    fun afterAll() {
        stopMockOAuthServer()
    }
}