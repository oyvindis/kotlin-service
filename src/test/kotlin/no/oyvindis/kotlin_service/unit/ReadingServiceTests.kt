package no.oyvindis.kotlin_service.unit

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import no.oyvindis.kotlin_service.repository.LocationDAO
import no.oyvindis.kotlin_service.repository.ReadingDAO
import no.oyvindis.kotlin_service.service.mapForCreation
import no.oyvindis.kotlin_service.service.ReadingService
import no.oyvindis.kotlin_service.utils.LOCATION_ID_1000
import no.oyvindis.kotlin_service.utils.READING_0
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@Tag("unit")
class ReadingServiceTests {
    private val readingDAO: ReadingDAO = mock()
    private val locationDAO: LocationDAO = mock()
    private val readingService = ReadingService(readingDAO, locationDAO)

    @Test
    fun `Get all readings by location id` () {
        whenever(readingDAO.findReadingsByLocation(LOCATION_ID_1000))
            .thenReturn(listOf(READING_0).map { it.mapForCreation(LOCATION_ID_1000)})

        val result = readingService.getReadingsByLocation(LOCATION_ID_1000)

        assertTrue { result.size == 1 }
    }
}