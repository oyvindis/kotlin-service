package no.oyvindis.kotlin_service.unit

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import no.oyvindis.kotlin_service.repository.LocationDAO
import no.oyvindis.kotlin_service.service.LocationService
import no.oyvindis.kotlin_service.service.toDBO
import no.oyvindis.kotlin_service.utils.LOCATION_0


import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@Tag("unit")
class LocationServiceTests {
    private val locationDAO: LocationDAO = mock()
    private val locationService = LocationService(locationDAO)

    @Test
    fun `Get all locations` () {
        whenever(locationDAO.findAll())
            .thenReturn(listOf(LOCATION_0).map { it.toDBO("id0") })

        val result = locationService.getLocations()

        assertTrue { result.size == 1 }
    }
}