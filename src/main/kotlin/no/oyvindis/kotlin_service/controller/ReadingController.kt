package no.oyvindis.kotlin_service.controller

import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.service.ReadingService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = LoggerFactory.getLogger(ReadingController::class.java)

@RestController
@CrossOrigin
@RequestMapping("/climate-api/reading/{locationId}")
class ReadingController (private val readingService: ReadingService) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createReading(
        @PathVariable locationId: String,
        @RequestBody reading: Reading
    ): ResponseEntity<Reading> {
        logger.info("creating reading for ${locationId}")
        return readingService.insert(reading, locationId)
            ?.let { ResponseEntity(it, locationHeaderForCreated(it), HttpStatus.CREATED) }
            ?: ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping
    fun getReadings(
        @PathVariable locationId: String
    ): ResponseEntity<List<Reading>> {
        logger.info("get reading for ${locationId}")
        return ResponseEntity<List<Reading>>(readingService.getReadingsByLocation(locationId), HttpStatus.OK)
    }
}

private fun locationHeaderForCreated(reading: Reading): HttpHeaders =
    HttpHeaders().apply {
        add(HttpHeaders.LOCATION, "/climate-api/reading/${reading.location}/${reading.id}")
        add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION)
    }