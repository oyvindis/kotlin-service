package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.repository.LocationDAO
import no.oyvindis.kotlin_service.utils.LOCATION_0
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertTrue

@Tag("unit")
class LocationServiceTest {
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
