package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.model.ReadingDBO
import no.oyvindis.kotlin_service.repository.LocationDAO
import no.oyvindis.kotlin_service.repository.ReadingDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private val logger: Logger = LoggerFactory.getLogger(ReadingService::class.java)

@Service
class ReadingService(
    private val readingDAO: ReadingDAO,
    private val locationDAO: LocationDAO
) {
    private fun createLocationIfNotExists(id: String) {
        try {
            if (!locationDAO.existsById(id)) {
                val locationDocument = LocationDBO(id = id)
                locationDAO.insert(locationDocument)
            }
        } catch (ex: Exception) {
           logger.error("insert location failed", ex)
        }
    }

    fun insert(reading: Reading, location: String): Reading? {
        createLocationIfNotExists(location)

        if (!locationDAO.existsById(location)) {
            throw object: Exception("Location not found") {}
        }
        val newReading: ReadingDBO = reading.mapForCreation(location)

        return readingDAO
            .insert(newReading)
            ?.let { it.toDTO(locationDAO.findById(it.location))}
    }

    fun getReadingsByLocation(locationId: String): List<Reading> = readingDAO.findReadingsByLocation(locationId)
        .map { it.toDTO(locationDAO.findById(it.location))}

}
