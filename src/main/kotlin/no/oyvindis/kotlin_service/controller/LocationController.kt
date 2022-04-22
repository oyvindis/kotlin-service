package no.oyvindis.kotlin_service.controller

import no.oyvindis.kotlin_service.model.Location
import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.service.LocationService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = LoggerFactory.getLogger(LocationController::class.java)

@RestController
@CrossOrigin
@RequestMapping("/climate-api/location")
class LocationController (private val locationService: LocationService) {
    @GetMapping
    fun getLocations(
    ): ResponseEntity<List<Location>> {
        logger.info("get locations")
        return ResponseEntity<List<Location>>(locationService.getLocations(), HttpStatus.OK)
    }
}

private fun locationHeaderForCreated(reading: Reading): HttpHeaders =
    HttpHeaders().apply {
        add(HttpHeaders.LOCATION, "/climate-api/location")
        add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION)
    }