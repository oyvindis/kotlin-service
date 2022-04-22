package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.model.ReadingDBO
import no.oyvindis.kotlin_service.repository.LocationDAO
import no.oyvindis.kotlin_service.repository.ReadingDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

private val logger: Logger = LoggerFactory.getLogger(ReadingService::class.java)

class AirthingsResponse {
    val data: Reading? = null
}

@Service
class ReadingService(
    private val readingDAO: ReadingDAO,
    private val locationDAO: LocationDAO,
    private val webClient: WebClient
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
            throw object : Exception("Location not found") {}
        }
        val newReading: ReadingDBO = reading.mapForCreation(location)

        return readingDAO
            .insert(newReading)
            ?.let { it.toDTO(locationDAO.findById(it.location)) }
    }

    fun getReadingsByLocation(locationId: String): List<Reading> = readingDAO.findReadingsByLocation(locationId)
        .map { it.toDTO(locationDAO.findById(it.location)) }

    @Scheduled(fixedRate = 21600000)
    fun postReadingFromAirthings() {
        logger.debug("postReadingFromAirthings")
        val data = webClient
            .get()
            .uri("https://ext-api.airthings.com/v1/devices/2960009475/latest-samples")
            .attributes(
                ServerOAuth2AuthorizedClientExchangeFilterFunction
                    .clientRegistrationId("airthings")
            )
            .retrieve()
            .bodyToMono<AirthingsResponse>()
        val responseStr = data.block()

        val newReading = responseStr?.data?.mapForCreation("2960009475")
        logger.debug(newReading.toString())

        if (newReading !== null) {
            readingDAO
                .insert(newReading).let { it.toDTO(locationDAO.findById(it.location)) }
        }

    }

}
