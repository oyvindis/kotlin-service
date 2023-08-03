package no.oyvindis.kotlin_service.controller

import no.oyvindis.kotlin_service.service.ReadingService
import no.oyvindis.kotlin_service.utils.LOCATION_ID_1000
import no.oyvindis.kotlin_service.utils.READING_0
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(ReadingController::class)
class ReadingControllerTest : ControllerTest() {

    @MockBean
    private lateinit var readingService: ReadingService

    @Test
    fun `Unauthorized when access token is not included`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/climate-api/reading/${LOCATION_ID_1000}")
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `list readings`() {
        val response = listOf(READING_0)

        whenever(readingService.getReadingsByLocation(LOCATION_ID_1000))
            .thenReturn(response)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/climate-api/reading/${LOCATION_ID_1000}")
                .header("Authorization", authoriazation)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(response.toJson()))

    }
}