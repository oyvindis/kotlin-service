package no.oyvindis.kotlin_service.controller

import no.oyvindis.kotlin_service.model.Location
import no.oyvindis.kotlin_service.service.LocationService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(LocationController::class)
class LocationControllerTest : ControllerTest() {

    @MockBean
    private lateinit var locationService: LocationService

    @Test
    fun `list locations`() {
        val response = listOf(Location("1", "Stue"))

        whenever(locationService.getLocations())
            .thenReturn(response)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/climate-api/location")
                .header("Authorization", authoriazation)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(response.toJson()))

        verify(locationService).getLocations()
    }
}